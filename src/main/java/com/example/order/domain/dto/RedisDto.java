package com.example.order.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record RedisDto(
        Long id,
        String orderState,
        LocalDateTime orderCreatedAt,
        List<String> menuName,
        int menuPrice,
        String orderDetail,
        int orderTotalAmount,
        int storeDeliveryFee,
        Map<String, List<String>> order
){}