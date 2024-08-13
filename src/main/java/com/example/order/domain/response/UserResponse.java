package com.example.order.domain.response;

import lombok.Builder;

@Builder
public record UserResponse(
    double customerLongitude,
    double customerLatitude,
    String customerNickname,
    Long customerId
) {
    public static UserResponse ToEntity(double customerLongitude, double customerLatitude,Long customerId,String customerNickname){

        return UserResponse.builder()
                .customerLongitude(customerLongitude)
                .customerLatitude(customerLatitude)
                .customerNickname(customerNickname)
                .customerId(customerId)
                .build();
    }
}
