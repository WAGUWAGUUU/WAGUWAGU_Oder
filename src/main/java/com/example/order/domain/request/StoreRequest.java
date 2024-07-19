package com.example.order.domain.request;


public record StoreRequest(
        Long id,
        Long ownerId,
        int pageNumber

) {}

