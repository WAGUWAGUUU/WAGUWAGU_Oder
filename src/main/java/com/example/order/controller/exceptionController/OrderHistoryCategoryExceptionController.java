package com.example.order.controller.exceptionController;

import com.example.order.domain.exception.OrderHistoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderHistoryCategoryExceptionController {

    @ExceptionHandler(OrderHistoryNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String OrderHistoryNotFoundExceptionHandler(OrderHistoryNotFoundException e){
        return e.getMessage();
    }
}
