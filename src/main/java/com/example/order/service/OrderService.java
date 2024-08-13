package com.example.order.service;


import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UpdateRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    void save(Order order);
    List<OrderHistory> selectByStoreDate(Long storeId, Timestamp startDate, Timestamp endDate,Long offset);
    List<OrderHistory> OrderHistoryFindByCustomerId(Long customerId,Long offset);
    void OrderHistoryDelete(Long id);
    List<Order> getOrderStoreId(Long requestId);
    List<Order> getOrderCustomerId(Long requestId);
    List<String> update(UUID id, UpdateRequest updateRequest);
    void delete(UUID id);
    UUID saveOrderReturnUUID(Order order);
}
