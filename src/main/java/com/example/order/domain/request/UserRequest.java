package com.example.order.domain.request;

import com.example.order.domain.entity.RedisOrder;

import java.time.LocalDateTime;
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
        String storeAddressString,
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

    public RedisOrder toEntity() {
        return RedisOrder.builder()
                .customerId(customerId)
                .ownerId(ownerId)
                .changeTime(changeTime)
                .orderState(orderState)
                .orderCreatedAt(orderCreatedAt)
                .customerAddress(customerAddress)
                .storePhone(storePhone)
                .storeName(storeName)
                .storeAddressString(storeAddressString)
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


}
