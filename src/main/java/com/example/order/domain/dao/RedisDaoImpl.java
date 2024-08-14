package com.example.order.domain.dao;

import com.example.order.domain.entity.Order;
import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.domain.type.StatusType;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisDaoImpl implements RedisDao {

    private final OrderRedIsRepository orderRedIsRepository;
    private final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void save(Order order) {
        orderRedIsRepository.save(order);
    }

    @Override
    public List<Order> getOrderStoreId(Long storeId) {
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
    public Order update(UUID id, String state, StatusType statusType) {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    log.debug("락 획득.");
                    Optional<Order> byId = orderRedIsRepository.findById(id.toString());
                    Order order = byId.orElseThrow(OrderNotFoundException::new);


                    Timestamp currentTime = new Timestamp(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(9));
                    order.setOrderState(state, currentTime);
                    order.setTimestamp(statusType, currentTime);

                    System.out.println("order.getOrderState(): " + order.getOrderState());


                    orderRedIsRepository.save(order);
                    return order;
                } finally {
                    lock.unlock();
                    log.debug("락 해제");
                }
            } else {
                throw new RuntimeException("락 시도를 하였지만 실패");
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

    @Override
    public UUID saveOrderReturnUUID(Order order) {
        Order savedOrder = orderRedIsRepository.save(order);
        return savedOrder.getOrderId();
    }

}
