package com.example.order.service;


import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UpdateRequest;
import com.example.order.domain.request.UserRequest;
import com.mongodb.client.result.UpdateResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    void save(UserRequest userRequest);
    List<OrderHistory> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber);
    List<OrderHistory> OrderHistoryFindByCustomerId(Long customerId);
    List<OrderHistory> OrderHistoryFindByOwnerId(Long storeId);
    UpdateResult OrderHistoryDelete(Long id);
//    OrderHistory OrderHistoryUpdate(Long id, String state);
    List<Order> get(Long requestId);
    List<Order> getOrderStoreId(Long requestId);
    List<Order> getOrderCustomerId(Long requestId);
    List<String> update(UUID id, UpdateRequest updateRequest);
    void delete(Long id);
}
