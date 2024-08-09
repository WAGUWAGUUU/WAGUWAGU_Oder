package com.example.order.domain.exception;

public class OrderHistoryNotFoundException extends IllegalArgumentException{
    public OrderHistoryNotFoundException() {super("OrderHistory not found");}
}
