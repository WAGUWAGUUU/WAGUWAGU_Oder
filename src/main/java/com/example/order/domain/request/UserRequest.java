package com.example.order.domain.request;

import com.example.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record UserRequest(
        Long customerId,
        Long storeId,
        Long ownerId,
        String storePhone,
        String storeName,
        String storeAddress,
        String customerRequests,
        String riderRequests,
        int storeDeliveryFee,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude,
        Long storeMinimumOrderAmount,
        String customerAddress,
        LocalDateTime due,
        List<Order.MenuItem> menuItems,
        String optionTitle,
        int optionPrice,
        String listName,
        List<Order.Option> options,
        String menuName,
        int totalPrice,
        List<Order.OptionList> selectedOptions
) {
    public Order toEntity() {
        return Order.builder()
                .customerId(customerId)
                .ownerId(ownerId)
                .storeId(storeId)
                .orderState(Collections.singletonList("CREATED:" + LocalDateTime.now()))
                .customerAddress(customerAddress)
                .storePhone(storePhone)
                .storeName(storeName)
                .storeAddress(storeAddress)
                .storeMinimumOrderAmount(storeMinimumOrderAmount)
                .customerRequests(customerRequests)
                .riderRequests(riderRequests)
                .storeDeliveryFee(storeDeliveryFee)
                .deliveryFee(deliveryFee)
                .distanceFromStoreToCustomer(distanceFromStoreToCustomer)
                .storeLongitude(storeLongitude)
                .storeLatitude(storeLatitude)
                .due(due)
                .menuItems(menuItems)
                .options(options)
                .OptionLists(selectedOptions.stream().map(ol -> Order.OptionList.builder()
                        .listName(ol.getListName())
                        .options(ol.getOptions())
                        .build()
                ).toList())
                .build();
    }
}
