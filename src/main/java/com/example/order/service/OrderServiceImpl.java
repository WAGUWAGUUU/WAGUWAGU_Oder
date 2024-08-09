package com.example.order.service;

import com.example.order.domain.dao.MongoDao;
import com.example.order.domain.dao.RedisDao;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UpdateRequest;
import com.example.order.domain.type.StatusType;
import com.example.order.kafka.dto.KafkaCartDTO;
import com.example.order.kafka.dto.KafkaDeliveryDTO;
import com.example.order.kafka.dto.KafkaSalesDTO;
import com.example.order.kafka.dto.KafkaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, KafkaStatus<KafkaDeliveryDTO>> kafkaDeliveryTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaCartDTO>> kafkaCartTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaSalesDTO>> kafkaSaleTemplate;
    private final RedisDao redisDao;
    private final MongoDao mongoDao;

    @Override
    public void save(Order order) {
        redisDao.save(order);
        KafkaCartDTO kafkaCartDTO = KafkaCartDTO.builder()
                .customerId(order.getCustomerId())
                .build();
        KafkaStatus<KafkaCartDTO> kafkaStatus = new KafkaStatus<>(kafkaCartDTO, StatusType.CREATED.name());
        kafkaCartTemplate.send("order-topic", kafkaStatus);
    }

    @Override
    public List<OrderHistory> selectByCustomerIdDate(Long id, Timestamp startDate, Timestamp endDate, int pageNumber) {

        return List.of();
    }

    @Transactional
    @Override
    public List<String> update(UUID id, UpdateRequest updateRequest) {
        System.out.println(updateRequest.due());
        StatusType statusType = StatusType.fromString(updateRequest.status());
        Order update = redisDao.update(id, statusType.getDisplayName());
        update.insertDau(updateRequest.due());
        System.out.println(statusType);
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
            KafkaStatus<KafkaDeliveryDTO> kafkaStatus = new KafkaStatus<>(kafkaDeliveryDTO, "insert");
            kafkaDeliveryTemplate.send("order-topic", kafkaStatus);
            return update.getOrderState();
        }else if (statusType == StatusType.DELIVERED) {
            OrderHistory orderHistory = OrderHistory.convertToOrderHistory(update);
            KafkaSalesDTO kafkaSalesDTO = KafkaSalesDTO.builder()
                    .storeId(update.getStoreId())
                    .sales(update.getOrderTotalPrice())
                    .time(new Timestamp(System.currentTimeMillis()))
                    .build();
            KafkaStatus<KafkaSalesDTO> kafkaStatus = new KafkaStatus<>(kafkaSalesDTO,"sales");
            kafkaSaleTemplate.send("order-topic", kafkaStatus);
            mongoDao.save(orderHistory);
            redisDao.delete(update.getOrderId());
            return update.getOrderState();
        }else if (statusType == StatusType.DELIVERING){
            update.insertRiderId(updateRequest.riderId());
            redisDao.save(update);
            return update.getOrderState();
        }

        return update.getOrderState();
    }


    @Override
    public List<OrderHistory> selectByStoreDate(Long id, Timestamp startDate, Timestamp endDate, int pageNumber) {
        return List.of();
    }

    @Override
    public List<OrderHistory> OrderHistoryFindByCustomerId(Long customerId) {
        return mongoDao.findByCustomerId(customerId);
    }

    @Override
    public List<OrderHistory> OrderHistoryFindByOwnerId(Long storeId) {
        return List.of();
    }


    @Override
    public void OrderHistoryDelete(Long id) {
        mongoDao.delete(id);
    }


    @Override
    public List<Order> getOrderStoreId(Long requestId) {
        return redisDao.getOrderStoreId(requestId);
    }

    @Override
    public List<Order> getOrderCustomerId(Long requestId) {
        return redisDao.getOrderCustomerId(requestId);
    }


    @Transactional
    @Override
    public void delete(UUID id) {
        redisDao.delete(id);
    }

    @KafkaListener(topics = "order-topic")
    public void synchronization(KafkaStatus<KafkaCartDTO> status) {
        System.out.println(status.status());
    }
}
