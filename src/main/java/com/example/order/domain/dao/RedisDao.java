package com.example.order.domain.dao;

import com.example.order.domain.entity.Order;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisDao implements RedisDaoImpl{

    private final OrderRedIsRepository orderRedIsRepository;

    @Override
    public Order save(Order order) {
        return orderRedIsRepository.save(order);
    }

    @Override
    public List<Order> get(Long ownerId) {
        System.out.println("Fetching order with requestId: " + ownerId);

        List<Order> allOrders = orderRedIsRepository.findAll();


        List<Order> filteredOrders = allOrders.stream()
                .filter(order -> order.getStoreId().equals(ownerId))
                .collect(Collectors.toList());
        return filteredOrders;
    }


    public Order update(Long id, String state) {

        Optional<Order> optionalOrder = orderRedIsRepository.findById(id.toString());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderState(state, LocalDateTime.now());
            orderRedIsRepository.save(order);
            return order;
        } else {
            throw new RuntimeException("아이디를 못 찾았습니다 " + id);
        }
    }

//        try {
//
//            if (lock.tryLock(3, TimeUnit.SECONDS)) {
//                try {
//                    System.out.println("락을 획득했습니다.");
//
//                    redisOrder.setOrderState(state, LocalDateTime.now());
//                    orderRedIsRepository.save(redisOrder);
//
//                } finally {
//                    lock.unlock();
//                }
//            } else {
//                System.out.println("락 획득에 실패했습니다.");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        return redisOrder;
//    }

    @Override
    public void delete(Long id) {
        ReentrantLock lock = new ReentrantLock(true);
        Optional<Order> byId = orderRedIsRepository.findById(String.valueOf(id));
        Order order = byId.orElseThrow(() -> new RuntimeException("아이디를 못 찾았습니다 " + id));

        try {

            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    System.out.println("락을 획득했습니다.");

                    orderRedIsRepository.delete(order);

                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("락 획득에 실패했습니다.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
