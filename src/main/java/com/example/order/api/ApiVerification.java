//package com.example.order.api;
//
//
//import com.example.order.domain.request.StoreRequest;
//import com.example.order.domain.response.StoreResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ApiVerification {
//
//    private final FeinVerification feinVerification;
//
//    @Async
//    public StoreResponse get() {
//        StoreResponse storeResponse = feinVerification.getVerification();
//        return storeResponse;
//
//    }
//
//    @Async
//    public StoreRequest send(){
//        StoreRequest storeRequest = feinVerification.sendVerification();
//        return storeRequest;
//    }
//
//
//
//
//}
