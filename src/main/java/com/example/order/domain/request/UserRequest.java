package com.example.order.domain.request;

import com.example.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .orderState(Collections.singletonList("주문 요청:" + LocalDateTime.now()))
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
                .menuItems(menuItems != null ? menuItems : Collections.emptyList())
                .options(options != null ? options : Collections.emptyList())
                .optionLists(selectedOptions != null ? selectedOptions.stream().map(ol -> Order.OptionList.builder()
                        .listName(ol.getListName())
                        .options(ol.getOptions() != null ? ol.getOptions() : Collections.emptyList())
                        .build()
                ).collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }
}
