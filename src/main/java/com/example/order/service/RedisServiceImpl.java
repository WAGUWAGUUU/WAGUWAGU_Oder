package com.example.order.service;

import com.example.order.domain.dao.RedisDao;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;
import com.example.order.kafka.dto.KafkaCartDTO;
import com.example.order.kafka.dto.KafkaDeliveryDTO;
import com.example.order.kafka.dto.KafkaStatus;
import com.example.order.domain.type.StatusType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final KafkaTemplate<String, KafkaStatus<KafkaDeliveryDTO>> kafkaDeliveryTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaCartDTO>> kafkaCartTemplate;
    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @Override
    public void save(UserRequest userRequest) {

        RedisOrder redisOrder = userRequest.toEntity();

        redisOrder.setOrderState(StatusType.CREATED.getDisplayName(), LocalDateTime.now());
        redisOrder.serializationOrder(userRequest.menuNameList(), objectMapper);
        redisDao.save(redisOrder);
        KafkaCartDTO kafkaCartDTO = KafkaCartDTO.builder()
                .customerId(userRequest.customerId())
                .build();
        KafkaStatus<KafkaCartDTO> kafkaStatus = new KafkaStatus<>(kafkaCartDTO, StatusType.CREATED.name());
        kafkaCartTemplate.send("order-topic", kafkaStatus);
    }

    @Override
    public List<RedisOrder> get(Long requestId) {
        return redisDao.get(requestId);
    }

    @Override
    public RedisOrder update(UUID id, String state) {
        StatusType statusType = StatusType.fromString(state);

        RedisOrder update = redisDao.update(id, statusType.getDisplayName());

        if (statusType == StatusType.DELIVERING) {
            KafkaDeliveryDTO kafkaDeliveryDTO = KafkaDeliveryDTO.builder()
                    .orderId(update.getOwnerId())
                    .storeName(update.getStoreName())
                    .deliveryFee(update.getDeliveryFee())
                    .distanceFromStoreToCustomer(update.getDistanceFromStoreToCustomer())
                    .storeLatitude(update.getStoreLatitude())
                    .storeLongitude(update.getStoreLongitude())
                    .due(update.getDue()).build();

            KafkaStatus<KafkaDeliveryDTO> kafkaStatus = new KafkaStatus<>(kafkaDeliveryDTO, "insert");
            kafkaDeliveryTemplate.send("order-topic", kafkaStatus);
        }



        return update;
    }

    @Override
    public void delete(Long id) {
        redisDao.delete(id);
    }

    @KafkaListener(topics = "order-topic")
    public void synchronization(KafkaStatus<KafkaCartDTO> status) {
        System.out.println(status.status());
    }
}
