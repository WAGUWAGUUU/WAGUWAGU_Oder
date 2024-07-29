package com.example.order.service;

import com.example.order.domain.dao.MongoDao;
import com.example.order.domain.dao.RedisDao;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UserRequest;
import com.example.order.domain.type.StatusType;
import com.example.order.kafka.dto.KafkaCartDTO;
import com.example.order.kafka.dto.KafkaDeliveryDTO;
import com.example.order.kafka.dto.KafkaStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, KafkaStatus<KafkaDeliveryDTO>> kafkaDeliveryTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaCartDTO>> kafkaCartTemplate;
    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;
    private final MongoDao mongoDao;

    @Override
    public void save(UserRequest userRequest) {

        Order order = userRequest.OrderToEntity();
        order.serializationOrder(userRequest.menuNameList(), objectMapper);
        redisDao.save(order);
        KafkaCartDTO kafkaCartDTO = KafkaCartDTO.builder()
                .customerId(userRequest.customerId())
                .build();
        KafkaStatus<KafkaCartDTO> kafkaStatus = new KafkaStatus<>(kafkaCartDTO, StatusType.CREATED.name());
        kafkaCartTemplate.send("order-topic", kafkaStatus);
    }

    @Transactional
    @Override
    public Order update(Long id, String state) {
        StatusType statusType = StatusType.fromString(state);
        Order update = redisDao.update(id, statusType.getDisplayName());
        System.out.println(update.getStoreAddress());
        System.out.println(update.getStoreName());

        if (statusType == StatusType.DELIVERY_REQUEST) {
            KafkaDeliveryDTO kafkaDeliveryDTO = KafkaDeliveryDTO.builder()
                    .orderId(update.getOrderId())
                    .storeName(update.getStoreName())
                    .storeAddress(update.getStoreAddress())
                    .deliveryFee(update.getDeliveryFee())
                    .distanceFromStoreToCustomer(update.getDistanceFromStoreToCustomer())
                    .storeLatitude(update.getStoreLatitude())
                    .storeLongitude(update.getStoreLongitude())
                    .due(update.getDue()).build();
            System.out.println(kafkaDeliveryDTO.orderId());
            KafkaStatus<KafkaDeliveryDTO> kafkaStatus = new KafkaStatus<>(kafkaDeliveryDTO, "insert");
            kafkaDeliveryTemplate.send("order-topic", kafkaStatus);
        }

        if (statusType == StatusType.COOKED) {
            OrderHistory orderHistory = OrderHistory.convertToOrderHistory(update);
            mongoDao.save(orderHistory);
        }

        return update;
    }

    
    @Override
    public List<OrderHistory> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber) {
        return mongoDao.selectByDate(id,startDate,endDate,pageNumber,10);
    }

    @Override
    public List<OrderHistory> OrderHistoryFindByCustomerId(Long customerId) {
        return mongoDao.findByCustomerId(customerId);
    }

    @Override
    public List<OrderHistory> OrderHistoryFindByOwnerId(Long ownerId) {
        return mongoDao.findByOwnerId(ownerId);
    }

    @Override
    public UpdateResult OrderHistoryDelete(Long id) {
        return mongoDao.delete(id);
    }

    @Override
    public OrderHistory OrderHistoryUpdate(Long id, String state) {
        StatusType statusType = StatusType.fromString(state);
        return mongoDao.update(id,statusType.getDisplayName());
    }


    @Override
    public List<Order> get(Long requestId) {
        return redisDao.get(requestId);
    }


    @Transactional
    @Override
    public void delete(Long id) {
        redisDao.delete(id);
    }

    @KafkaListener(topics = "order-topic")
    public void synchronization(KafkaStatus<KafkaCartDTO> status) {
        System.out.println(status.status());
    }
}
