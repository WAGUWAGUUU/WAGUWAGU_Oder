package com.example.order.controller.exceptionController;

import com.example.order.domain.exception.CustomerNotFoundException;
import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.domain.exception.StatusTypeNotFoundException;
import com.example.order.domain.exception.StoreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderCategoryExceptionController {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String OrderNotFoundExceptionHandler(OrderNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(StatusTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String StatusTypeNotFoundExceptionHandler(StatusTypeNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String StoreNotFoundExceptionHandler(StoreNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String CustomerNotFoundExceptionHandler(CustomerNotFoundException e){
        return e.getMessage();
    }



}
