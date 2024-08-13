package com.example.order.service;

import com.example.order.domain.dao.MongoDao;
import com.example.order.domain.dao.RedisDao;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.exception.StatusTypeNotFoundException;
import com.example.order.domain.request.UpdateRequest;
import com.example.order.domain.type.StatusType;
import com.example.order.kafka.dto.*;
import com.example.order.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaProducer kafkaProducer;
    private final RedisDao redisDao;
    private final MongoDao mongoDao;

    @Override
    public void save(Order order) {
        redisDao.save(order);
        KafkaCartDTO kafkaCartDTO = KafkaCartDTO.builder()
                .customerId(order.getCustomerId())
                .build();
        kafkaProducer.KafkaCartSend(kafkaCartDTO, StatusType.CREATED.name());
    }

    @Transactional
    @Override
    public List<String> update(UUID id, UpdateRequest updateRequest) {
        StatusType statusType = StatusType.fromString(updateRequest.status());
        Order update = redisDao.update(id, statusType.getDisplayName(),statusType);
        KafkaPushReqDTO kafkaPushReqDTO = KafkaPushReqDTO.builder()
                .customerId(3613397573L)
                .build();
        kafkaProducer.KafkaPushReqSend(kafkaPushReqDTO, String.valueOf(statusType));

            switch (statusType) {
                case DELIVERY_REQUEST:
                    update.updateDue(updateRequest.due());
                    redisDao.save(update);
                    KafkaDeliveryDTO kafkaDeliveryDTO = KafkaDeliveryDTO.builder()
                            .orderId(update.getOrderId())
                            .storeName(update.getStoreName())
                            .storeAddress(update.getStoreAddress())
                            .deliveryFee(update.getDeliveryFee())
                            .distanceFromStoreToCustomer(update.getDistanceFromStoreToCustomer())
                            .storeLatitude(update.getStoreLatitude())
                            .storeLongitude(update.getStoreLongitude())
                            .due(updateRequest.due()).build();
                    kafkaProducer.KafkaDeliverySend(kafkaDeliveryDTO, "insert");
                    break;
                case DELIVERING:
                    update.updateRiderId(updateRequest.riderId());
                    redisDao.save(update);
                    break;

                case DELIVERED:
                    OrderHistory orderHistory = OrderHistory.convertToOrderHistory(update);
                    KafkaSalesDTO kafkaSalesDTO = KafkaSalesDTO.builder()
                            .storeId(update.getStoreId())
                            .sales(update.getOrderTotalPrice())
                            .time(new Timestamp(System.currentTimeMillis()))
                            .build();
                    kafkaProducer.KafkaSalesSend(kafkaSalesDTO, "sales");
                    mongoDao.save(orderHistory);
                    redisDao.delete(update.getOrderId());
                    break;
                default:
                    break;
            }

        return update.getOrderState();
    }

    @Override
    public List<OrderHistory> selectByStoreDate(Long storeId, Timestamp startDate, Timestamp endDate, Long offset) {
        return mongoDao.selectByStoreDate(storeId, startDate, endDate, offset, 10);
    }

    @Override
    public List<OrderHistory> OrderHistoryFindByCustomerId(Long customerId ,Long offset) {
        return mongoDao.findByCustomerId(customerId,offset);
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
        log.debug(status.status());
    }
}
