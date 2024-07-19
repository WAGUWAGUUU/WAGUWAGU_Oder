package com.example.order.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RedisHash("RedisOrder")
@Builder
@Getter
@Slf4j
public class RedisOrder {

    @Id
    private Long orderId;
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
    private LocalDateTime due;


    public void setOrderState(String state, LocalDateTime changeTime) {


        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
        this.changeTime = changeTime;
    }

    public void serializationOrder(String order, ObjectMapper objectMapper) {
        try {
            this.order = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }



}
