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
    private LocalDateTime orderCreatedAt;

    private String customerAddress;

    private String storePhone;
    private String storeName;
    private String storeAddressString;

    private HashMap<String,List<String>> menuNameList;
    private String menuName;
    private String menuIntroduction;
    private int menuPrice;

    private HashMap<String,List<String>> optionTitleLIst;
    private String optionTitle;
    private int optionPrice;
    private String listName;
    private HashMap<String,List<String>> listNameList;
    private String options;

    private String customerRequests;
    private String riderRequests;
    private String order;

    private int orderTotalAmount;
    private int storeDeliveryFee;
    private int deliveryFee;
    private double distanceFromStoreToCustomer;
    private double storeLongitude;
    private double storeLatitude;
    @Setter
    private boolean isDeleted;
    private LocalDateTime due;

    private OrderList menuItem;



    public void setOrderState(String state, LocalDateTime changeTime) {

        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
        this.changeTime = changeTime;
    }

    @Builder
    @Getter
    public static class OrderList {
        private HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String,List<String>>>>>>>> menuNameList;


    }

}