package com.example.order.domain.dao;


import com.example.order.domain.entity.RedisOrder;

import java.util.List;
import java.util.UUID;

public interface RedisDaoImpl {

    RedisOrder save(RedisOrder redisOrder);
    List<RedisOrder> get(Long ownerId);
    RedisOrder update(UUID id, String state);
    void delete(Long id);

}
