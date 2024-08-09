package com.example.order.kafka.dto;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
public record KafkaDeliveryDTO(
        UUID orderId,
        String storeName,
        String storeAddress,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude,
        Timestamp due

) {
}
