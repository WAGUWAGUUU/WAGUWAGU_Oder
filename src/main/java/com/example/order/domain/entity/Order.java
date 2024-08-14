package com.example.order.domain.entity;

import com.example.order.domain.type.StatusType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    private String due;
    private double distanceFromStoreToCustomer;
    private String reasonForCancellation;

    private String riderRequests;
    private int orderTotalPrice;

    private String CREATED;
    private String COOKING;
    private String COOKED;
    private String DELIVERY_REQUEST;
    private String DELIVERING;
    private String DELIVERED;
    private String CANCEL;
    private String ACCEPT_DELIVERY;

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

    public void setTimestamp(StatusType statusType, Timestamp timestamp) {
        String timestampStr = timestamp.toString();
        switch (statusType) {
            case CREATED:
                this.CREATED = timestampStr;
                break;
            case COOKING:
                this.COOKING = timestampStr;
                break;
            case COOKED:
                this.COOKED = timestampStr;
                break;
            case DELIVERY_REQUEST:
                this.DELIVERY_REQUEST = timestampStr;
                break;
            case DELIVERING:
                this.DELIVERING = timestampStr;
                break;
            case DELIVERED:
                this.DELIVERED = timestampStr;
                break;
            case CANCEL:
                this.CANCEL = timestampStr;
                break;
            case ACCEPT_DELIVERY:
                this.ACCEPT_DELIVERY = timestampStr;
                break;
            default:
                break;
        }
    }


    public void setOrderState(String state, Timestamp changeTime) {
        if (this.orderState == null) {
            this.orderState = new ArrayList<>();
        }
        this.orderState.add(state + ":" + changeTime.toString());
    }


    public void updateRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public void updateDue(Timestamp due) {
        Timestamp adjustedDue = new Timestamp(due.getTime() + TimeUnit.HOURS.toMillis(9));
        this.due = adjustedDue.toString();
    }

}