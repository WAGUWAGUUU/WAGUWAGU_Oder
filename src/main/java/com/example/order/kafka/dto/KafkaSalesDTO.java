package com.example.order.kafka.dto;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record KafkaSalesDTO(
        Long storeId,
        Timestamp time,
        int sales
) {
}
