package com.example.order.domain.exception;

public class StoreNotFoundException extends IllegalArgumentException{
    public StoreNotFoundException() {
        super("Store not found");
    }
}
