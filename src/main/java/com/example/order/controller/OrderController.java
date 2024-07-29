package com.example.order.controller;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderHistory;
import com.example.order.domain.request.UserRequest;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public String orderSave(@RequestBody UserRequest userRequest) {
        orderService.save(userRequest);
        return "주문내역 저장 성공";
    }

    @PostMapping("/request/{orderId}")
    public Order updateOrder(@PathVariable Long id, @RequestBody String status){
        Order update = orderService.update(id, status);

        return update;
    }

    @GetMapping("/consumer/{consumerId}")
    public List<OrderHistory> selectByConsumerAll(@PathVariable Long consumerId) {
        return orderService.OrderHistoryFindByCustomerId(consumerId);
    }

    @GetMapping("/store/{storeId}")
    public List<OrderHistory> selectByStoreAll(@PathVariable Long storeId) {
        return orderService.OrderHistoryFindByOwnerId(storeId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{requestId}/history")
    public List<OrderHistory> selectByDate(@PathVariable Long requestId,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                           @RequestParam int pageNumber
                                    ){

    List<OrderHistory> orderHistories = orderService.selectByDate(requestId, startDate, endDate, pageNumber);
        return orderHistories;
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{orderId}")
    public String delete(@PathVariable Long orderId) {
        orderService.OrderHistoryDelete(orderId);
        return "주문건 삭제 성공";
    }

    @GetMapping("/{requestId}")
    public  List<Order> getOrder1(@PathVariable Long requestId) {
        List<Order> orders = (List<Order>) orderService.get(requestId);
        return orders;
    }



    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        orderService.delete(id);
        return "주문건 삭제 성공";
    }




}
