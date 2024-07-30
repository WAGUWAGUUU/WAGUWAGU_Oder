package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;

import java.util.List;
import java.util.UUID;

public interface RedisDaoImpl {

    Order save(Order order);
    List<Order> get(Long ownerId);
    List<Order> getOrderStoreId(Long storeId);
    List<Order> getOrderCustomerId(Long customerId);
    Order update(UUID id, String state);
    void delete(Long id);

}
