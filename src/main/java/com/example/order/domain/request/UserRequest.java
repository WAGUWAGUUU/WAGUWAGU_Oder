package com.example.order.domain.request;

import java.util.List;

public record UserRequest(
        Long customerId,
        Long ownerId,
        String orderState,
        int menuPrice,
        int orderTotalAmount,
        int storeDeliveryFee,
        String optionTitle,
        String customerRequests,
        String riderRequests,
        String order,
        List<String> menuName,
        String storeName,
        String storeAddress,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude
) {
    public record MenuItem(
            String menuName,
            List<String> optionTitle
    ) {}
}