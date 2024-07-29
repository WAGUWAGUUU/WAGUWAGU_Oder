package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;

import java.util.List;

public interface RedisDaoImpl {

    Order save(Order order);
    List<Order> get(Long ownerId);
    Order update(Long id, String state);
    void delete(Long id);

}
