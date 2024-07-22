package com.example.order.service;


import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;

public interface OrderService {

    void createOrder(UserRequest userRequest);
    RedisOrder upDateOrder(Long id, String status);






}
