package com.example.order.domain.dao;


import com.example.order.domain.entity.RedisOrder;

public interface RedisDaoImpl {

    RedisOrder save(RedisOrder redisOrder);
    RedisOrder get(Long id);
    RedisOrder update(Long id, String state);
    void delete(Long id);

}
