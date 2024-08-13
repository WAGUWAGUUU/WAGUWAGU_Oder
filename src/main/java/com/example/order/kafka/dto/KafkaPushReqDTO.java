package com.example.order.kafka.dto;

import lombok.Builder;

@Builder
public record KafkaPushReqDTO(
        Long customerId
)
{
}