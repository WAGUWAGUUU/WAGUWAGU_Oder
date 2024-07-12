//package com.example.order.api;
//
//import com.example.order.domain.request.StoreRequest;
//import com.example.order.domain.response.StoreResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@FeignClient(name = "store",url = "192.168.0.192:8080")
//public interface FeinVerification {
//
//    @PostMapping("api/v1/store/{cartId}")
//    StoreRequest sendVerification();
//
//    @GetMapping("api/v1/store/{cartId}")
//    StoreResponse getVerification();
//
//}
