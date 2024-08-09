package com.example.order.domain.request;


import java.sql.Timestamp;

public record UpdateRequest(
        String status,
        Long riderId,
        Timestamp due
) {
}
