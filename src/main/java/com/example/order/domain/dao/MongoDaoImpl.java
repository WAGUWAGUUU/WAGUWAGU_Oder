package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;


import java.time.LocalDate;
import java.util.List;


public interface MongoDaoImpl{

    List<Order> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize);
    List<Order> findById(Long id);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOwnerId(Long ownerId);
    void save(Order order);
    UpdateResult delete(ObjectId id);
}
