package com.example.order.domain.request;

import java.util.List;

public record UserRequest(
        Long storeId,
        String storePhone,
        String storeName,
        String storeAddress,
        Long storeMinimumOrderAmount,
        String customerRequests,
        String riderRequests,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude,
        List<MenuItem> menuItems,
        int orderTotalPrice,
        List<OptionList> selectedOptions
) {

    public record MenuItem(
            String menuName,
            int totalPrice,
            List<OptionList> selectedOptions
    ) {}

    public record OptionList(
            String listName,
            List<Option> options
    ) {}

    public record Option(
            String optionTitle,
            int optionPrice
    ) {}
}