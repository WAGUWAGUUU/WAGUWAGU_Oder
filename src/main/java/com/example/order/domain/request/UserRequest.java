package com.example.order.domain.request;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.type.StatusType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public record UserRequest(
        Long customerId,
        Long ownerId,
        LocalDateTime changeTime,
        List<String> orderState,
        LocalDateTime orderCreatedAt,
        String storePhone,
        String storeName,
        String storeAddress,
        String menuName,
        String menuIntroduction,
        int menuPrice,
        String optionTitle,
        int optionPrice,
        String listName,
        String options,
        String customerRequests,
        String riderRequests,
        String order,
        int orderTotalAmount,
        int storeDeliveryFee,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude,
        LocalDateTime due,
        String customerAddress,
        HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String,List<String>>>>>>>> menuNameList
) {

    public Order OrderToEntity() {
        return Order.builder()
                .customerId(customerId)
                .storeId(ownerId)
                .orderState(orderState)
                .orderCreatedAt(orderCreatedAt)
                .orderState(Collections.singletonList(StatusType.CREATED.getDisplayName()))
                .customerAddress(customerAddress)
                .storePhone(storePhone)
                .storeName(storeName)
                .storeAddress(storeAddress)
                .menuName(menuName)
                .menuIntroduction(menuIntroduction)
                .menuPrice(menuPrice)
                .optionTitle(optionTitle)
                .optionPrice(optionPrice)
                .listName(listName)
                .options(options)
                .customerRequests(customerRequests)
                .riderRequests(riderRequests)
                .orderTotalAmount(orderTotalAmount)
                .storeDeliveryFee(storeDeliveryFee)
                .deliveryFee(deliveryFee)
                .distanceFromStoreToCustomer(distanceFromStoreToCustomer)
                .storeLongitude(storeLongitude)
                .storeLatitude(storeLatitude)
                .due(due)
                .menuNameList(String.valueOf(menuNameList))
                .build();
    }

    public OrderHistory OrderHistoryToEntity() {
        String menuItem = menuName + " " + optionTitle;
        return OrderHistory.builder()
                .menuItem(menuItem)
                .storeId(ownerId)
                .customerId(customerId)
                .orderState(Collections.singletonList(StatusType.CREATED.getDisplayName()))
                .orderCreatedAt(LocalDateTime.now())
                .customerAddress(customerAddress)
                .customerRequests(customerRequests)
                .riderRequests(riderRequests)
                .storeName(storeName)
                .storeLongitude(storeLongitude)
                .storeLatitude(storeLatitude)
                .distanceFromStoreToCustomer(distanceFromStoreToCustomer)
                .storeDeliveryFee(storeDeliveryFee)
                .deliveryFee(deliveryFee)
                .isDeleted(false)
                .build();
    }

}


