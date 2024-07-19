package com.example.order.domain.dao;



import com.example.order.domain.entity.RedisOrder;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisDao implements RedisDaoImpl{

    private final OrderRedIsRepository orderRedIsRepository;

    @Override
    public RedisOrder save(RedisOrder redisOrder) {
        return orderRedIsRepository.save(redisOrder);
    }

    @Override
    public RedisOrder get(Long id) {
        Optional<RedisOrder> byId = orderRedIsRepository.findById(String.valueOf(id));

        return byId.orElseThrow(() -> new RuntimeException("아이디를 못 찾았습니다 " + id));
    }

    @Override
    public RedisOrder update(Long id, String state) {
        Optional<RedisOrder> byId = orderRedIsRepository.findById(String.valueOf(id));
        RedisOrder redisOrder = byId.orElseThrow(() -> new RuntimeException("아이디를 못 찾았습니다 " + id));
        redisOrder.setOrderState(state, LocalDateTime.now());
        orderRedIsRepository.save(redisOrder);
        return redisOrder;
    }

    @Override
    public void delete(Long id) {
        Optional<RedisOrder> byId = orderRedIsRepository.findById(String.valueOf(id));
        RedisOrder redisOrder = byId.orElseThrow(() -> new RuntimeException("아이디를 못 찾았습니다 " + id));

        orderRedIsRepository.delete(redisOrder);

    }
}
