package com.example.order.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.*;

@Document(collection = "OrderHistory")@Builder@Slf4j@Getter
public class OrderHistory {

    @Id
    private ObjectId orderId;

    private Long customerId;
    private String customerAddress;
    private String customerRequests;

    private Long riderId;

    private Long storeId;
    private String storeName;
    private String storeIntroduction;
    private String storeAddress;
    private String storePhone;
    private Long storeMinimumOrderAmount;
    private int deliveryFee;
    private double storeLongitude;
    private double storeLatitude;
    private Timestamp due;
    private double distanceFromStoreToCustomer;
    private String reasonForCancellation;
    private String riderRequests;
    private int orderTotalPrice;
    @Setter
    private boolean isDeleted;
    private List<String> orderState;
    private List<Order.MenuItem> menuItems;
    private String menuName;
    private int totalPrice;
    private List<Order.OptionList> selectedOptions;
    private String listName;
    private List<Order.Option> options;
    private String optionTitle;
    private int optionPrice;




    public static OrderHistory convertToOrderHistory(Order order) {
        return OrderHistory.builder()
                .isDeleted(false)
                .customerId(order.getCustomerId())
                .storeId(order.getStoreId())
                .customerAddress(order.getCustomerAddress())
                .customerRequests(order.getCustomerRequests())
                .riderRequests(order.getRiderRequests())
                .storeName(order.getStoreName())
                .storePhone(order.getStorePhone())
                .storeIntroduction(order.getStoreIntroduction())
                .storeAddress(order.getStoreAddress())
                .storeMinimumOrderAmount(order.getStoreMinimumOrderAmount())
                .deliveryFee(order.getDeliveryFee())
                .storeLongitude(order.getStoreLongitude())
                .storeLatitude(order.getStoreLatitude())
                .distanceFromStoreToCustomer(order.getDistanceFromStoreToCustomer())
                .due(order.getDue())
                .orderTotalPrice(order.getOrderTotalPrice())
                .reasonForCancellation(order.getReasonForCancellation())
                .orderState(order.getOrderState() != null ? order.getOrderState() : new ArrayList<>())
                .options(order.getOptions() != null ? order.getOptions() : new ArrayList<>())
                .menuItems(order.getMenuItems() != null ? order.getMenuItems() : new ArrayList<>())
                .build();
    }

}