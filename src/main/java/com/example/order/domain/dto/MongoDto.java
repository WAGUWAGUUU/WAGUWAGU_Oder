package com.example.order.domain.dto;

import java.time.LocalDateTime;
import java.util.List;


public record MongoDto(
        Long id,
        Long customerId,
        Long ownerId,
        String orderState,
        LocalDateTime orderCreatedAt,
        int menuPrice,
        int orderTotalAmount,
        int storeDeliveryFee,
        List<MenuItem> order // List of menu items
) {
    public record MenuItem(
            String menuName,
            List<String> menuOption
    ) {}
}