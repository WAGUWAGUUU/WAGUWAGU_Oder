package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;
import com.example.order.domain.type.StatusType;

import java.util.List;
import java.util.UUID;

public interface RedisDaoImpl {

    void save(Order order);
    List<Order> getOrderStoreId(Long storeId);
    List<Order> getOrderCustomerId(Long customerId);
    Order update(UUID id, String state, StatusType statusType);
    void delete(UUID id);

}
