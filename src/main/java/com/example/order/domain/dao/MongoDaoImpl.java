package com.example.order.domain.dao;

import com.example.order.domain.entity.OrderHistory;
import com.mongodb.client.result.UpdateResult;

import java.time.LocalDate;
import java.util.List;


public interface MongoDaoImpl{

    List<OrderHistory> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize);
    List<OrderHistory> findByCustomerId(Long customerId);
    List<OrderHistory> findByOwnerId(Long ownerId);
    void save(OrderHistory orderHistory);
    OrderHistory update(Long id, String state);
    UpdateResult delete(Long id);
}
