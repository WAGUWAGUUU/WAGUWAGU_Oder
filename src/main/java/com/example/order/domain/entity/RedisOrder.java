package com.example.order.domain.entity;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


import java.time.LocalDateTime;

@RedisHash("RedisOrder")
@Builder
@Data
public class RedisOrder {

    @Id
    private Long id;

    private String orderState;
    private LocalDateTime orderCreatedAt;
    private String menuName;
    private int menuPrice;
    private String orderDetail;
    private int orderTotalAmount;
    private int storeDeliveryFee;

}