package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;

import java.util.Optional;

public interface RedisService {

   Optional<RedisOrder> save(RedisDto redisDto);
    Optional<RedisOrder> get(long id);

}
