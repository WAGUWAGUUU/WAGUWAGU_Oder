package com.example.order.service;

import com.example.order.domain.dto.StateDto;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;

import java.util.Optional;

public interface RedisService {

   Optional<RedisOrder> save(RedisDto redisDto);
   Optional<RedisOrder> get(Long id);
   Optional<RedisOrder> update(Long id, StateDto stateDto);
   Optional<RedisOrder> delete(Long id, StateDto stateDto);
}
