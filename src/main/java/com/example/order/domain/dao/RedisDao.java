package com.example.order.domain.dao;


import com.example.order.domain.entity.RedisOrder;
import com.example.order.repository.OrderRedIsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisDao implements OrderRedIsRepository{

    private final OrderRedIsRepository orderRedIsRepository;
    private final ObjectMapper objectMapper;


    @Override
    public void delete(Optional<RedisOrder> byId) {

    }

    @Override
    public RedisOrder findById(Long id) {
        return null;
    }

    @Override
    public <S extends RedisOrder> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RedisOrder> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<RedisOrder> findById(String string) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String string) {
        return false;
    }

    @Override
    public Iterable<RedisOrder> findAll() {
        return null;
    }

    @Override
    public Iterable<RedisOrder> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String string) {

    }

    @Override
    public void delete(RedisOrder entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends RedisOrder> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
