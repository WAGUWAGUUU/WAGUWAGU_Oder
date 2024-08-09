package com.example.order.domain.dao;

import com.example.order.domain.entity.Order;
import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisDao implements RedisDaoImpl{

    private final OrderRedIsRepository orderRedIsRepository;
    private final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void save(Order order) {
        orderRedIsRepository.save(order);
    }

    @Override
    public List<Order> getOrderStoreId(Long storeId) {
        System.out.println("requestId: " + storeId);
        List<Order> allOrders = orderRedIsRepository.findAll();
        return allOrders.stream()
                .filter(order -> order.getStoreId().equals(storeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrderCustomerId(Long customerId) {
        log.debug("customerId: " + customerId);
        List<Order> allOrders = orderRedIsRepository.findAll();
        return allOrders.stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    @Override
    public Order update(UUID id, String state) {

        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    log.debug("락 획듯.");
                    Optional<Order> byId = orderRedIsRepository.findById(id.toString());
                    Order order = byId.orElseThrow(OrderNotFoundException::new);
                    order.setOrderState(state, new Timestamp(System.currentTimeMillis()));
                    orderRedIsRepository.save(order);
                    return order;
                } finally {
                    lock.unlock();
                    log.debug("락 해제");
                }
            } else {
                throw new RuntimeException("락시도를 하였지만 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 시도 재실행", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            Optional<Order> byId = orderRedIsRepository.findById(String.valueOf(id));
            Order order = byId.orElseThrow(OrderNotFoundException::new);
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    log.debug("락을 획득");
                    orderRedIsRepository.delete(order);
                } finally {
                    lock.unlock();
                    log.debug("락 해제");
                }
            } else {
               log.debug("락 획득 실패");
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
