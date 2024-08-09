package com.example.order.domain.exception;

public class StatusTypeNotFoundException extends IllegalArgumentException{
    public StatusTypeNotFoundException() {
        super("StatusType not found");
    }
}
