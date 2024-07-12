package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.RedisDto;

public interface RedisService {

    void save(RedisDto redisDto);
    RedisOrder get(RedisDto redisDto);

}
