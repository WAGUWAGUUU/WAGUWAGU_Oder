package com.example.order.domain.dto;

import com.example.order.domain.entity.Order;
import com.example.order.domain.request.UserRequest;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record OrderDto (
        Long customerId,
        Long storeId,
        String storePhone,
        String storeName,
        String storeAddress,
        String customerRequests,
        String riderRequests,
        int deliveryFee,
        double distanceFromStoreToCustomer,
        double storeLongitude,
        double storeLatitude,
        int totalPrice,
        List<Order.MenuItem> menuItems,
        List<Order.OptionList> selectedOptions
) {
    public static Order toEntity(UserRequest userRequest, Long customerId, String customerAddress) {
        return Order.builder()
                .customerId(customerId)
                .customerAddress(customerAddress)
                .riderId(0L)
                .storeId(userRequest.storeId())
                .orderState(Collections.singletonList("주문 요청:" + new Timestamp(System.currentTimeMillis())))
                .storePhone(userRequest.storePhone())  // storePhone이 null이더라도 그대로 전달
                .storeName(userRequest.storeName())
                .storeAddress(userRequest.storeAddress())
                .storeMinimumOrderAmount(userRequest.storeMinimumOrderAmount())  // 이 값은 null일 수 있음
                .customerRequests(userRequest.customerRequests())
                .riderRequests(userRequest.riderRequests())
                .deliveryFee(userRequest.deliveryFee())
                .distanceFromStoreToCustomer(userRequest.distanceFromStoreToCustomer())
                .storeLongitude(userRequest.storeLongitude())
                .storeLatitude(userRequest.storeLatitude())
                .orderTotalPrice(userRequest.orderTotalPrice())

                // menuItems 처리
                .menuItems(userRequest.menuItems() != null ? userRequest.menuItems().stream().map(item ->
                        Order.MenuItem.builder()
                                .menuName(item.menuName())
                                .totalPrice(item.totalPrice())
                                .selectedOptions(item.selectedOptions() != null ? item.selectedOptions().stream().map(optionList ->
                                        Order.OptionList.builder()
                                                .listName(optionList.listName())
                                                .options(optionList.options() != null ? optionList.options().stream().map(option ->
                                                        Order.Option.builder()
                                                                .optionTitle(option.optionTitle())
                                                                .optionPrice(option.optionPrice())
                                                                .build()
                                                ).collect(Collectors.toList()) : Collections.emptyList())
                                                .build()
                                ).collect(Collectors.toList()) : Collections.emptyList())
                                .build()
                ).collect(Collectors.toList()) : Collections.emptyList())

                .optionLists(userRequest.selectedOptions() != null ? userRequest.selectedOptions().stream().map(ol ->
                        Order.OptionList.builder()
                                .listName(ol.listName())
                                .options(ol.options() != null ? ol.options().stream().map(option ->
                                        Order.Option.builder()
                                                .optionTitle(option.optionTitle())
                                                .optionPrice(option.optionPrice())
                                                .build()
                                ).collect(Collectors.toList()) : Collections.emptyList())
                                .build()
                ).collect(Collectors.toList()) : Collections.emptyList())

                .build();
    }
}
