package com.example.order.controller;

import com.example.order.domain.dto.OrderDto;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UpdateRequest;
import com.example.order.domain.request.UserRequest;
import com.example.order.domain.response.UserResponse;
import com.example.order.global.utils.JwtUtil;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userInformation")
    public UserResponse userInformation(@RequestHeader("Authorization") String token){
        String bearerToken = token.substring(7);
        return UserResponse.ToEntity(jwtUtil.getCustomerFromToken(bearerToken).getCustomerLongitude(),
                jwtUtil.getCustomerFromToken(bearerToken).getCustomerLatitude(), jwtUtil.getCustomerFromToken(bearerToken).getCustomerId());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public String orderSave(@RequestHeader("Authorization") String token,@RequestBody UserRequest userRequest) {
        String bearerToken = token.substring(7);
        Long customerId = jwtUtil.getCustomerFromToken(bearerToken).getCustomerId();
        String customerAddress = jwtUtil.getCustomerFromToken(bearerToken).getCustomerAddress();
        Order order = OrderDto.toEntity(userRequest, customerId, customerAddress);
        orderService.save(order);
        return "주문내역 저장 성공";
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer")
    public  List<Order> getOrderCustomerId(@RequestHeader("Authorization") String token) {
        String bearerToken = token.substring(7);
        Long customerId = jwtUtil.getCustomerFromToken(bearerToken).getCustomerId();
        return orderService.getOrderCustomerId(customerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/store/{storeId}/all")
    public  List<Order> getOrderStoreId(@PathVariable Long storeId) {
        return orderService.getOrderStoreId(storeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/request/{orderId}")
    public List<String> updateOrder(@PathVariable("orderId") UUID id, @RequestBody UpdateRequest updateRequest) {
        return orderService.update(id,updateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/history")
    public List<OrderHistory> selectByConsumerAll(@RequestHeader("Authorization") String token ,@RequestParam Long offset) {
        String bearerToken = token.substring(7);
        Long customerId = jwtUtil.getCustomerFromToken(bearerToken).getCustomerId();
        return orderService.OrderHistoryFindByCustomerId(customerId,offset);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{storeId}/history")
    public List<OrderHistory> selectByStoreDate(
            @PathVariable Long storeId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Long offset
    ) {
        Timestamp startTimestamp = new Timestamp(Long.parseLong(startDate));
        Timestamp endTimestamp = new Timestamp(Long.parseLong(endDate));
        return orderService.selectByStoreDate(storeId, startTimestamp, endTimestamp, offset);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/{orderId}/history")
    public String delete(@PathVariable Long orderId) {
        orderService.OrderHistoryDelete(orderId);
        return "주문건 삭제 성공";
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/history")
//    public List<OrderHistory> selectByCustomerIdDate(@RequestHeader("Authorization") String token,
//                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp startDate,
//                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp endDate,
//                                           @RequestParam int pageNumber
//                                    ){
//    String bearerToken = token.substring(7);
//    Long customerId = jwtUtil.getCustomerFromToken(bearerToken).getCustomerId();
//    List<OrderHistory> orderHistories = orderService.selectByCustomerIdDate(customerId, startDate, endDate, pageNumber);
//        return orderHistories;
//    }

}
