package com.example.order.domain.request;


import java.time.LocalDateTime;

public record StoreRequest(
        Long id,
        Long ownerId,
        int pageNumber

) {}

