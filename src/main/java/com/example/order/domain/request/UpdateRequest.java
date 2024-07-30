package com.example.order.domain.request;

public record UpdateRequest(
        String state,
        Long riderId
) {
}
