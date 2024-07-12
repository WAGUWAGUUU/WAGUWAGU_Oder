package com.example.order.service;


import com.example.order.domain.entity.Order;
import com.example.order.domain.dto.MongoDto;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(MongoDto mongoDto) {

        Order order = Order.builder()
                .id(4L)
                .orderDetail("test디테일")
                .orderState("주문상태")
                .orderCreatedAt(LocalDateTime.now())
                .build();

        mongoTemplate.save(order);
    }

    @Override
    public Order get() {
        MongoCollection<Document> order = mongoTemplate.getCollection("Order");
        return (Order) order;
    }
}
