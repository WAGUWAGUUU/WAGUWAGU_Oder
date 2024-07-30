package com.example.order.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "OrderHistory")@Builder@Slf4j@Getter
public class OrderHistory {

    @Id
    private ObjectId orderId;

    private Long customerId;
    private String customerAddress;
    private String customerRequests;
    private Long ownerId;
    private Long riderId;

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
    private String reasonForCancellation;

    private String riderRequests;
    private int deliveryFee;

    private LocalDateTime orderCreatedAt;
    @Setter
    private boolean isDeleted;
    @Setter
    private List<String> orderState;

    private Map<String, List<String>> menuNameList;
    private List<Order.Option> options;
    private List<Order.OptionList> optionLists;
    private List<Order.MenuItem> menuItems;
    private String menuName;
    private int totalPrice;
    private List<Order.OptionList> selectedOptions;
    private String listName;
    private String optionTitle;
    private int optionPrice;

    public static OrderHistory convertToOrderHistory(Order order) {
        return OrderHistory.builder()
                .customerId(order.getCustomerId())
                .customerAddress(order.getCustomerAddress())
                .customerRequests(order.getCustomerRequests())
                .ownerId(order.getOwnerId())
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
                .reasonForCancellation(order.getReasonForCancellation())
                .riderRequests(order.getRiderRequests())
                .deliveryFee(order.getDeliveryFee())
                .orderState(order.getOrderState() != null ? order.getOrderState() : new ArrayList<>())
                .isDeleted(false)
                .options(order.getOptions() != null ? order.getOptions() : new ArrayList<>())
                .optionLists(order.getOptionLists() != null ? order.getOptionLists() : new ArrayList<>())
                .menuItems(order.getMenuItems() != null ? order.getMenuItems() : new ArrayList<>())
                .menuName(order.getMenuName() != null ? order.getMenuName() : "")
                .totalPrice(order.getTotalPrice())
                .selectedOptions(order.getSelectedOptions() != null ? order.getSelectedOptions() : new ArrayList<>())
                .listName(order.getListName() != null ? order.getListName() : "")
                .optionTitle(order.getOptionTitle() != null ? order.getOptionTitle() : "")
                .optionPrice(order.getOptionPrice())
                .build();
    }

    public void setOrderState(String state, LocalDateTime changeTime) {
        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
    }
}