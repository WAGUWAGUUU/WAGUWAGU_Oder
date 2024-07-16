package com.example.order.service;

import com.example.order.domain.entity.Order;
import com.example.order.domain.dto.MongoDto;
import com.mongodb.client.result.UpdateResult;

import java.time.LocalDateTime;
import java.util.List;

public interface MongoService {
     void save(MongoDto mongoDto);
     List<Order> get(Long id);
     List<Order> selectByDate(Long id,LocalDateTime startDate, LocalDateTime endDate, int pageNumber);
     List<Order> findByCustomerId(Long customerId);
     List<Order> findByOwnerId(Long ownerId);
     UpdateResult delete(Long id);
}
