package com.example.order.domain.response;

import lombok.Builder;

@Builder
public record UserResponse(
    double customerLongitude,
    double customerLatitude,
    Long customerId
) {
    public static UserResponse ToEntity(double customerLongitude, double customerLatitude,Long customerId){

        return UserResponse.builder()
                .customerLongitude(customerLongitude)
                .customerLatitude(customerLatitude)
                .customerId(customerId)
                .build();
    }
}
