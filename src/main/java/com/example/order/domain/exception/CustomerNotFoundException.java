package com.example.order.domain.exception;

public class CustomerNotFoundException extends IllegalArgumentException{
    public CustomerNotFoundException() {super("Customer not found");}
}
