package com.example.order.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record RedisDto(
        @JsonProperty("id") Long id,
        @JsonProperty("orderState") String orderState,
        @JsonProperty("orderCreatedAt") LocalDateTime orderCreatedAt,
        @JsonProperty("menuName") List<String> menuName,
        @JsonProperty("menuPrice") int menuPrice,
        @JsonProperty("orderDetail") String orderDetail,
        @JsonProperty("orderTotalAmount") int orderTotalAmount,
        @JsonProperty("storeDeliveryFee") int storeDeliveryFee,
        @JsonProperty("order") Map<String, List<String>> order // Ensure correct definition
) implements Serializable {
}