package com.example.order.kafka.dto;

import com.example.order.domain.type.RiderTransportation;

import java.util.UUID;

public record KafkaPaymentDto(
        UUID orderId,
        Long riderId,
        int deliveryFee,
        String storeName,
        Long storeId,
        int foodPrice,
        RiderTransportation riderTransportation,
        double distanceFromStoreToCustomer
) {
}
