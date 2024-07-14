package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;
import com.example.order.repository.OrderRedIsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService{


    private final OrderRedIsRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<RedisOrder> save(RedisDto redisDto) {

        RedisOrder redisOrder = RedisOrder.builder()
                .id(redisDto.id())
                .menuName(redisDto.menuName())
                .orderCreatedAt(redisDto.orderCreatedAt())
                .orderState(redisDto.orderState())
                .menuEachPrice(redisDto.menuPrice())
                .orderTotalAmount(redisDto.orderTotalAmount())
                .storeDeliveryFee(redisDto.storeDeliveryFee())
                .build();

        redisOrder.setOrder(redisDto.order(), objectMapper);

        repository.save(redisOrder);
        return Optional.of(redisOrder);
    }


    @Override
    public Optional<RedisOrder> get(long redisOrder) {
        Optional<RedisOrder> byId = repository.findById(String.valueOf(redisOrder));

        if (byId.isEmpty()) {
            return Optional.empty();
        }

        return byId;
    }
}
