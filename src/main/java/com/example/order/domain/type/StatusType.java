package com.example.order.domain.type;

import com.example.order.domain.exception.StatusTypeNotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StatusType {
    CREATED("주문 요청"),
    COOKING("조리중"),
    COOKED("조리완료"),
    DELIVERY_REQUEST("배달요청"),
    DELIVERING("배달중"),
    DELIVERED("배달 완료"),
    CANCEL("주문취소"),
    ACCEPT_DELIVERY("배달 수락");

    private static final Map<String, StatusType> STRING_TO_ENUM = new HashMap<>();

    static {
        for (StatusType status : values()) {
            STRING_TO_ENUM.put(status.displayName.toLowerCase(), status);
        }
    }

    private final String displayName;

    StatusType(String displayName) {
        this.displayName = displayName;
    }

    public static StatusType fromString(String type) {
        StatusType status = STRING_TO_ENUM.get(type.toLowerCase());
        if (status == null) {
            throw new StatusTypeNotFoundException();
        }
        return status;
    }
}
