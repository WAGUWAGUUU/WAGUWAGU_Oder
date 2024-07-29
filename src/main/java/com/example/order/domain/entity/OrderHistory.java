package com.example.order.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Document(collection = "OrderHistory")
@Builder
@Slf4j
@Getter
public class OrderHistory {

    @Id
    private ObjectId orderId;

    private Long customerId;
    private String customerAddress;
    private String customerRequests;

    private Long storeId;
    private String storePhone;
    private String storeName;
    private String storeIntroduction;

    private String storeAddress;
    private Long storeMinimumOrderAmount;
    private int storeDeliveryFee;
    private double storeLongitude;
    private double storeLatitude;
    private LocalDateTime due;
    private double distanceFromStoreToCustomer;
    private String ReasonForCancellation;

    private String riderRequests;
    private int deliveryFee;

    private LocalDateTime orderCreatedAt;
    @Setter
    private boolean isDeleted;
    @Setter
    private List<String> orderState;

    private HashMap<String, List<String>> menuNameList;
    private String menuItem;

    public static OrderHistory convertToOrderHistory(Order order) {
        return OrderHistory.builder()
                .customerId(order.getCustomerId())
                .customerAddress(order.getCustomerAddress())
                .customerRequests(order.getCustomerRequests())
                .storeId(order.getStoreId())
                .storePhone(order.getStorePhone())
                .storeName(order.getStoreName())
                .storeIntroduction(order.getStoreIntroduction())
                .storeAddress(order.getStoreAddress())
                .storeMinimumOrderAmount(order.getStoreMinimumOrderAmount())
                .storeDeliveryFee(order.getStoreDeliveryFee())
                .storeLongitude(order.getStoreLongitude())
                .storeLatitude(order.getStoreLatitude())
                .due(order.getDue())
                .distanceFromStoreToCustomer(order.getDistanceFromStoreToCustomer())
                .ReasonForCancellation(order.getReasonForCancellation())
                .riderRequests(order.getRiderRequests())
                .deliveryFee(order.getDeliveryFee())
                .orderCreatedAt(order.getOrderCreatedAt())
                .orderState(order.getOrderState())
                .menuItem(order.getMenuName() + " " + order.getOptionTitle())
                .isDeleted(false)
                .build();
    }

    public void setOrderState(String state, LocalDateTime changeTime) {
        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
    }

    @Builder
    @Getter
    public static class OrderList {
        private HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String, List<String>>>>>>>> menuNameList;
    }
}
