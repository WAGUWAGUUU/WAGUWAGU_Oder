package com.example.order.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record MongoDto(
        Long id,
        String orderState,
        LocalDateTime orderCreatedAt,
        int menuPrice,
        int orderTotalAmount,
        int storeDeliveryFee,
        List<MenuItem> order // List of menu items
) {
    public static record MenuItem(
            String menuName,
            List<String> menuOption
    ) {}
}