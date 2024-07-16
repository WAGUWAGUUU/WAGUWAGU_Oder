package com.example.order.service;

//import com.example.order.api.ApiVerification;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.StoreRequest;
import com.example.order.domain.response.StoreResponse;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MongoTemplate mongoTemplate;
    private final OrderRedIsRepository redIsRepository;


    @Override
    public void statusTracking() {



    }


    @Override
    public Order history() {
        return null;
    }


}
