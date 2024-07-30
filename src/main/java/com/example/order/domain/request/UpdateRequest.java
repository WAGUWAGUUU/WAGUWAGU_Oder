package com.example.order.domain.request;

public record UpdateRequest(
        String status,
        Long riderId
) {
}
