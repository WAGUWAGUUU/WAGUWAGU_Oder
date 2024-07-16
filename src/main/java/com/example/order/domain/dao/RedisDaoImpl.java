package com.example.order.domain.dao;

import com.example.order.domain.dto.RedisDto;
import com.example.order.domain.dto.StateDto;
import com.example.order.domain.entity.RedisOrder;

import java.util.Optional;

public interface RedisDaoImpl {

    Optional<RedisOrder> save(RedisDto redisDto);
    Optional<RedisOrder> get(Long id);
    Optional<RedisOrder> update(Long id, StateDto stateDto);
    Optional<RedisOrder> delete(Long id, StateDto stateDto);


}
