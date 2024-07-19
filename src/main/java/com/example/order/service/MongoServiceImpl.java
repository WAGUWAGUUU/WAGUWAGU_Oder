package com.example.order.service;

import com.example.order.domain.dao.MongoDao;
import com.example.order.domain.entity.Order;
import com.example.order.domain.request.UserRequest;
import com.example.order.domain.type.StatusType;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    private final MongoDao mongoDao;

    @Override
    public void save(UserRequest userRequest) {
        LocalDateTime dateTime = LocalDateTime.of(2024, 7, 15, 15, 30, 0);
        Order order = Order.builder()
                .ownerId(userRequest.ownerId())
                .customerId(userRequest.customerId())
                .orderState(Collections.singletonList(StatusType.CREATED.getDisplayName()))
                .orderCreatedAt(dateTime)
                .orderTotalAmount(userRequest.orderTotalAmount())
                .storeDeliveryFee(userRequest.storeDeliveryFee())
                .storeName(userRequest.storeName())
                .menuName(userRequest.menuName())
                .optionTitle(userRequest.optionTitle())
                .customerRequests(userRequest.customerRequests())
                .riderRequests(userRequest.riderRequests())
                .storeAddress(userRequest.storeAddress())
                .deliveryFee(userRequest.deliveryFee())
                .distanceFromStoreToCustomer(userRequest.distanceFromStoreToCustomer())
                .storeLongitude(userRequest.storeLongitude())
                .storeLatitude(userRequest.storeLatitude())
                .isDeleted(false)
                .build();
        mongoDao.save(order);
    }

    @Override
    public List<Order> get(Long id) {
        return mongoDao.findById(id);
    }

    @Override
    public List<Order> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber) {
        return mongoDao.selectByDate(id,startDate,endDate,pageNumber,10);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return mongoDao.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findByOwnerId(Long ownerId) {
        return mongoDao.findByOwnerId(ownerId);
    }

    @Override
    public UpdateResult delete(ObjectId id) {
        return mongoDao.delete(id);
    }
}
