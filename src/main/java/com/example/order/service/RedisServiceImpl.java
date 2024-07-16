package com.example.order.service;

import com.example.order.domain.dto.StateDto;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;
import com.example.order.repository.OrderRedIsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
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

        redisOrder.serializationOrder(redisDto.order(), objectMapper);

        repository.save(redisOrder);
        return Optional.of(redisOrder);
    }


    @Override
    public Optional<RedisOrder> get(Long redisOrder) {
        Optional<RedisOrder> byId = repository.findById(String.valueOf(redisOrder));
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        return byId;
    }

    @Override
    public Optional<RedisOrder> update(Long id, StateDto stateDto) {

        Optional<RedisOrder> byId = repository.findById(String.valueOf(id));
        if (byId.isEmpty()) {
            return Optional.empty();
        }

        RedisOrder redisOrder = RedisOrder.builder()
                                .id(id)
                                .order(byId.get().getOrder())
                                .orderState(stateDto.status())
                                .orderCreatedAt(byId.get().getOrderCreatedAt())
                                .riderRequests(byId.get().getRiderRequests())
                                .menuEachPrice(byId.get().getMenuEachPrice())
                                .build();

        repository.save(redisOrder);


        return Optional.of(redisOrder);

    }

    @Override
    public Optional<RedisOrder> delete(Long id, StateDto stateDto) {
        Optional<RedisOrder> byId = repository.findById(String.valueOf(id));
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        repository.delete(byId);

        return Optional.empty();
    }
}
