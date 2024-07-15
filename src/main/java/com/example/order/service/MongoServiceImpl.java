package com.example.order.service;


import com.example.order.domain.entity.Order;
import com.example.order.domain.dto.MongoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{


    private final MongoTemplate mongoTemplate;


    @Override
    public void save(MongoDto mongoDto) {

        List<Order.MenuItem> orderList = mongoDto.order().stream()
                .map(menuItem -> Order.MenuItem.builder()
                        .menuName(menuItem.menuName())
                        .menuOption(menuItem.menuOption())
                        .build())
                .collect(Collectors.toList());

        Order order = Order.builder()
                .id(mongoDto.id())
                .orderState(mongoDto.orderState())
                .orderCreatedAt(mongoDto.orderCreatedAt())
                .menuEachPrice(mongoDto.menuPrice())
                .orderTotalAmount(mongoDto.orderTotalAmount())
                .storeDeliveryFee(mongoDto.storeDeliveryFee())
                .order(orderList)
                .build();

        mongoTemplate.save(order);
    }

    @Override
    public Order get(Long id) {
        return mongoTemplate.findById(id, Order.class);
    }
}
