package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService{

    private final OrderRedIsRepository repository;

    @Override
    public void save(RedisDto redisDto) {

        RedisOrder redisOrder = RedisOrder.builder()
                .id(3L)
                .orderDetail("고객요청사항")
                .orderState("주문하는상태")
                .orderCreatedAt(LocalDateTime.now())
                .build();

        repository.save(redisOrder);

    }

    @Override
    public RedisOrder get(RedisDto redisOrder) {

        Iterable<RedisOrder> all = repository.findAll();

        return (RedisOrder) all;
    }
}
