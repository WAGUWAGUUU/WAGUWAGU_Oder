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
import java.util.Collections;

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
        RedisOrder redisOrder = RedisOrder.builder()
                .customerId(userRequest.customerId())
                .ownerId(userRequest.ownerId())
                .menuName(userRequest.menuName())
                .orderCreatedAt(LocalDateTime.now())
                .menuEachPrice(userRequest.menuPrice())
                .orderTotalAmount(userRequest.orderTotalAmount())
                .storeDeliveryFee(userRequest.storeDeliveryFee())
                .storeName(userRequest.storeName())
                .storeAddress(userRequest.storeAddress())
                .deliveryFee(userRequest.deliveryFee())
                .distanceFromStoreToCustomer(userRequest.distanceFromStoreToCustomer())
                .storeLatitude(userRequest.storeLatitude())
                .storeLongitude(userRequest.storeLongitude())
                .due(LocalDateTime.now()).build();

        redisOrder.setOrderState(StatusType.CREATED.getDisplayName(), LocalDateTime.now());
        redisOrder.serializationOrder(userRequest.order(), objectMapper);
        redisDao.save(redisOrder);
        KafkaCartDTO kafkaCartDTO = KafkaCartDTO.builder()
                .customerId(userRequest.customerId())
                .build();
        KafkaStatus<KafkaCartDTO> kafkaStatus = new KafkaStatus<>(kafkaCartDTO, StatusType.CREATED.name());
        kafkaCartTemplate.send("order-topic", kafkaStatus);
    }

    @Override
    public RedisOrder get(Long id) {
        return redisDao.get(id);
    }

    @Override
    public RedisOrder update(Long id, String state) {
        StatusType statusType = StatusType.fromString(state);

        RedisOrder update = redisDao.update(id, statusType.getDisplayName());

        update.setOrderState(statusType.getDisplayName(), LocalDateTime.now());

        if (statusType == StatusType.DELIVERING) {
            KafkaDeliveryDTO kafkaDeliveryDTO = KafkaDeliveryDTO.builder()
                    .orderId(update.getOrderId())
                    .storeName(update.getStoreName())
                    .storeAddress(update.getStoreAddress())
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
