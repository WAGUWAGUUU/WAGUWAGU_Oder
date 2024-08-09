package com.example.order.domain.dao;

import com.example.order.domain.entity.OrderHistory;
import com.mongodb.client.result.UpdateResult;
import java.sql.Timestamp;
import java.util.List;


public interface MongoDaoImpl{

    List<OrderHistory> selectByCustomerIdDate(Long customerId, Timestamp startDate, Timestamp endDate, int pageNumber, int pageSize);
    List<OrderHistory> selectByStoreDate(Long storeId, Timestamp startDate, Timestamp endDate, int pageNumber, int pageSize);
    List<OrderHistory> findByCustomerId(Long customerId);
    List<OrderHistory> findByStoreId(Long ownerId);
    void save(OrderHistory orderHistory);
    UpdateResult delete(Long id);
}
