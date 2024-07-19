package com.example.order.service;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;

public interface OrderService {

    void statusTracking();
    Order history();
    void deliveryKafkaEvent();



}
