package com.example.order.kafka.dto;

public record KafkaStatus<T>(
        T data, String status
) {
}