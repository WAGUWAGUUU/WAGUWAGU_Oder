package com.example.order.domain.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@RedisHash("Order")
@Builder
@Getter
@Slf4j
public class Order {

    @Id
    private UUID orderId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long customerId;
    private String customerAddress;
    private String customerRequests;
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long storeId;

    private String storeName;
    private String storeIntroduction;
    private Long riderId;
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
    private List<String> orderState;
    private List<Option> options;
    private List<OptionList> optionLists;
    private List<MenuItem> menuItems;

    @Builder
    @Getter
    public static class MenuItem {
        private String menuName;
        private int totalPrice;
        private List<OptionList> selectedOptions;
    }

    @Builder
    @Getter
    public static class OptionList {
        private String listName;
        private List<Option> options;
    }

    @Builder
    @Getter
    public static class Option {
        private String optionTitle;
        private int optionPrice;
    }


    public void setOrderState(String state, Timestamp changeTime) {
        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
    }



    public Order insertRiderId(Long riderId) {
        return Order.builder()
                .riderId(riderId)

                .build();
    }

    public Order insertDau(Timestamp dau) {
        return Order.builder()
                .due(dau)
                .build();
    }

}