package com.example.order.domain.request;

import java.time.LocalDateTime;

public record StoreRequest(
       Long orderId,
       Long customerId,
       Long storeId,
       Long storeMinimumOrderAmount,
       int storeDeliveryFee,
       LocalDateTime due,
       String ReasonForCancellation,
       int deliveryFee
) {}

