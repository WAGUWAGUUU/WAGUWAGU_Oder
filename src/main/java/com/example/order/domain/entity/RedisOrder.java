package com.example.order.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RedisHash("RedisOrder")
@Builder
@Getter
@Slf4j
public class RedisOrder {

    @Id
    private UUID orderId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long customerId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long ownerId;
    @Setter
    private LocalDateTime changeTime;
    @Setter
    private List<String> orderState;
    private LocalDateTime orderCreatedAt;

    private String storePhone;
    private String storeName;
    private String storeAddressString;

    private String customerAddress;

    private String menuNameList;
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

    private int orderTotalAmount;
    private int storeDeliveryFee;
    private int deliveryFee;
    private double distanceFromStoreToCustomer;
    private double storeLongitude;
    private double storeLatitude;
    private LocalDateTime due;
    private Order.OrderList menuItem;



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

    // Method to serialize the menuItem field
    public void serializationOrder(HashMap<String, List<HashMap<String, List<HashMap<String, List<HashMap<String, List<String>>>>>>>> menuItem, ObjectMapper objectMapper) {
        try {
            this.menuNameList = objectMapper.writeValueAsString(menuItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
