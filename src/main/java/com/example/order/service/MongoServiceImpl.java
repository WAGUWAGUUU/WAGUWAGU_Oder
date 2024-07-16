package com.example.order.service;


import com.example.order.domain.dao.MongoDao;
import com.example.order.domain.dto.MongoDto;
import com.example.order.domain.entity.Order;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    private final MongoDao mongoDao;

    @Override
    public void save(MongoDto mongoDto) {

        List<Order.MenuItem> orderList = mongoDto.order().stream()
                .map(menuItem -> Order.MenuItem.builder()
                        .menuName(menuItem.menuName())
                        .menuOption(menuItem.menuOption())
                        .build())
                .collect(Collectors.toList());

        Order order = Order.builder()
                .id(mongoDto.id())
                .orderState(mongoDto.orderState())
                .orderCreatedAt(LocalDateTime.now())
                .menuEachPrice(mongoDto.menuPrice())
                .orderTotalAmount(mongoDto.orderTotalAmount())
                .storeDeliveryFee(mongoDto.storeDeliveryFee())
                .order(orderList)
                .build();

        mongoDao.save(order);
    }

    @Override
    public List<Order> get(Long id) {
        return mongoDao.findById(id);
    }

    @Override
    public List<Order> selectByDate(Long id,LocalDateTime startDate, LocalDateTime endDate, int pageNumber) {
        return mongoDao.selectByDate(id,startDate,endDate,pageNumber,10);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return mongoDao.findByOwnerId(customerId);
    }

    @Override
    public List<Order> findByOwnerId(Long ownerId) {
        return mongoDao.findByCustomerId(ownerId);
    }

    @Override
    public UpdateResult delete(Long id) {
        return mongoDao.delete(id);
    }
}
