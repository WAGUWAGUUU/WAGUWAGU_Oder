package com.example.order.domain.dao;

import com.example.order.domain.entity.Order;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface MongoDaoImpl{

    List<Order> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOwnerId(Long ownerId);
    void save(Order order);
    Order update(Long id, String state);
    UpdateResult delete(Long id);
}
