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
import java.util.List;

@Document(collection = "Order")
@Builder
@Slf4j
@Getter
public class Order {

    @Id
    private ObjectId orderId;
    private Long customerId;
    private Long ownerId;
    @Setter
    private LocalDateTime changeTime;
    @Setter
    private List<String> orderState;
    private String storeName;
    private LocalDateTime orderCreatedAt;
    private List<String> menuName;
    private String optionTitle;
    private int menuEachPrice;
    private String customerRequests;
    private String riderRequests;
    private String order;
    private int optionPrice;
    private int orderTotalAmount;
    private int storeDeliveryFee;
    private String storeAddress;
    private int deliveryFee;
    private double distanceFromStoreToCustomer;
    private double storeLongitude;
    private double storeLatitude;
    @Setter
    private boolean isDeleted;
    private LocalDateTime due;


    public void setOrderState(String state, LocalDateTime changeTime) {

        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
        this.changeTime = changeTime;
    }

}