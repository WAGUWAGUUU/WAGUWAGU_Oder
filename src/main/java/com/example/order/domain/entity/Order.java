package com.example.order.domain.entity;


import lombok.Builder;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;


@Document(collection = "Order")
@Builder
@Data
public class Order {

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
