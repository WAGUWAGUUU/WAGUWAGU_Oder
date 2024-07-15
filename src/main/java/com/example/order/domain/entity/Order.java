package com.example.order.domain.entity;


import lombok.Builder;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Document(collection = "Order")
@Builder
@Data
public class Order {

    @Id
    private Long id;
    private String orderState;
    private LocalDateTime orderCreatedAt;
    private int menuEachPrice;
    private int orderTotalAmount;
    private int storeDeliveryFee;
    private List<MenuItem> order;
    private String customerRequests;
    private String riderRequests;

    @Data
    @Builder
    public static class MenuItem {
        private String menuName;
        private List<String> menuOption;
    }

}