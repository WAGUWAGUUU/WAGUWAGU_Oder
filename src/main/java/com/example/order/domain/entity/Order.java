package com.example.order.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RedisHash("Order")
@Builder
@Getter
@Slf4j
public class Order {

    @Id
    private Long orderId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long customerId;
    private String customerAddress;
    private String customerRequests;

    @EqualsAndHashCode.Include
    @ToString.Include
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

    @Setter
    private List<String> orderState;
    private LocalDateTime orderCreatedAt;

    private String menuNameList;
    private String menuName;
    private String listName;
    private String menuIntroduction;

    private HashMap<String,List<String>> optionTitleLIst;
    private String optionTitle;
    private HashMap<String,List<String>> listNameList;
    private String options;
    private int optionPrice;
    private int menuPrice;
    private int orderTotalAmount;

    private OrderHistory.OrderList menuItem;


    public void setOrderState(String state, LocalDateTime changeTime) {
        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
    }



    @Builder
    @Getter
    public static class OrderList {
        private HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String,List<String>>>>>>>> menuNameList;

    }

    public void serializationOrder(HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String, List<String>>>>>>>> menuItem, ObjectMapper objectMapper) {
        try {
            this.menuNameList = objectMapper.writeValueAsString(menuItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
