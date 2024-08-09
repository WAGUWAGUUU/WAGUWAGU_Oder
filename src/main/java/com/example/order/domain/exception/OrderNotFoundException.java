package com.example.order.domain.exception;

public class OrderNotFoundException extends IllegalArgumentException{
    public OrderNotFoundException() {super("Order not found");}
}
