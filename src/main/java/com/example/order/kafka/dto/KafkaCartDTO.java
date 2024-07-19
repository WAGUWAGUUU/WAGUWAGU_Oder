package com.example.order.kafka.dto;

import lombok.Builder;

@Builder
public record KafkaCartDTO(
        Long customerId
) {
}
