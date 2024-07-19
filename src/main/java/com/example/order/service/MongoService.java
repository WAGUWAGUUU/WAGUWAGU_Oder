package com.example.order.service;

import com.example.order.domain.entity.Order;
import com.example.order.domain.request.UserRequest;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MongoService {
     void save(UserRequest userRequest);
     List<Order> get(Long id);
     List<Order> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber);
     List<Order> findByCustomerId(Long customerId);
     List<Order> findByOwnerId(Long ownerId);
     UpdateResult delete(ObjectId id);
}
