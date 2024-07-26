package com.example.order.domain.type;

public enum StatusType {

    CREATED("주문 요청"),
    COOKING("조리중"),
    COOKED("조리완료"),
    DELIVERY_REQUEST("배달요청"),
    DELIVERING("배달중"),
    DELIVERED("배달완료"),
    CANCEL("주문취소");

    private final String displayName;

    StatusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static StatusType fromString(String type) {
        switch(type.toLowerCase()) {
            case "주문 요청":
                return StatusType.CREATED;
            case "조리중":
                return StatusType.COOKING;
            case "조리 완료":
                return StatusType.COOKED;
            case "배달 요청":
                return StatusType.DELIVERY_REQUEST;
            case "배달중":
                return StatusType.DELIVERING;
            case "배달 완료":
                return StatusType.DELIVERED;
            case "주문 취소":
                return  StatusType.CANCEL;
            default:
                throw new IllegalArgumentException("Unknown status type: " + type);
        }
    }
}
