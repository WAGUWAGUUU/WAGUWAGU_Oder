package com.example.order.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RedisHash("RedisOrder")
@Builder
@Data
public class RedisOrder {

    @Id
    private Long id;

    private String orderState;
    private LocalDateTime orderCreatedAt;
    private List<String> menuName;
    private String order;
    private int menuEachPrice;
    private String customerRequests;
    private String riderRequests;
    private int orderTotalAmount;
    private int storeDeliveryFee;

    // Method to set the order as a JSON string
    public void setOrder(Map<String, List<String>> order, ObjectMapper objectMapper) {
        try {
            this.order = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
