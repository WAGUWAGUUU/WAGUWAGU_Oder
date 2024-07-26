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
        Order.OrderList menuItem = Order.OrderList.builder()
                .menuNameList(userRequest.menuNameList())
                .build();

        Order order = Order.builder()
                .ownerId(userRequest.ownerId())
                .customerId(userRequest.customerId())
                .orderState(Collections.singletonList(StatusType.CREATED.getDisplayName()))
                .orderCreatedAt(LocalDateTime.now())
                .orderTotalAmount(userRequest.orderTotalAmount())
                .customerAddress(userRequest.customerAddress())
                .customerRequests(userRequest.customerRequests())
                .riderRequests(userRequest.riderRequests())

                .menuName(userRequest.menuName())
                .optionTitle(userRequest.optionTitle())

                .storeName(userRequest.storeName())
                .storeAddressString(userRequest.storeAddressString())
                .storeLongitude(userRequest.storeLongitude())
                .storeLatitude(userRequest.storeLatitude())
                .distanceFromStoreToCustomer(userRequest.distanceFromStoreToCustomer())
                .storeDeliveryFee(userRequest.storeDeliveryFee())
                .deliveryFee(userRequest.deliveryFee())
                .menuItem(menuItem)
                .isDeleted(false)
                .build();

        mongoDao.save(order);
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
    public UpdateResult delete(Long id) {
        return mongoDao.delete(id);
    }

    @Override
    public Order update(Long id, String state) {
        StatusType statusType = StatusType.fromString(state);
        return mongoDao.update(id,statusType.getDisplayName());
    }
}
