package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;
import com.mongodb.client.result.UpdateResult;


import java.time.LocalDateTime;
import java.util.List;


public interface MongoDaoImpl{

    List<Order> selectByDate(Long id,LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int pageSize);
    List<Order> findById(Long id);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOwnerId(Long ownerId);
    void save(Order order);
    UpdateResult delete(Long id);
}
