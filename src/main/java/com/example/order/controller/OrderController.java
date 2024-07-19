package com.example.order.controller;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.PageRequest;
import com.example.order.domain.request.UserRequest;
import com.example.order.service.MongoService;
import com.example.order.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("api/v1/Order")
@RequiredArgsConstructor
public class OrderController {

    private final MongoService mongoService;
    private final RedisService redisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/history")
    public String orderSave(@RequestBody UserRequest userRequest) {
        mongoService.save(userRequest);

        return "주문내역 저장 성공";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{requestId}/history/{startDate}/{endDate}")
    public List<Order> selectByDate(@PathVariable Long requestId,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestBody PageRequest pageRequest ) {

        int pageNumber = pageRequest.pageNumber();

        List<Order> orders = mongoService.selectByDate(requestId, startDate, endDate, pageNumber);
        return orders;
    }

    @GetMapping("/consumer/{consumerId}/history")
    public List<Order> selectByConsumerAll(@PathVariable Long consumerId) {
        return mongoService.findByCustomerId(consumerId);
    }

    @GetMapping("/owner/{ownerId}/history")
    public List<Order> selectByOwnerAll(@PathVariable Long ownerId) {
        return mongoService.findByOwnerId(ownerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{orderId}")
    public String delete(@PathVariable ObjectId orderId) {
        mongoService.delete(orderId);
        return "주문건 삭제 성공";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/redis")
    public String createOrder(@RequestBody UserRequest userRequest) {
        redisService.save(userRequest);
        return "주무건 생성 성공";
    }

    public String DeliveryDriverAssignment(){

        return "fe";
    }

    @GetMapping("/redis/{id}")
    public RedisOrder getOrder1(@PathVariable Long id) {
        RedisOrder redisOrder = redisService.get(id);
        return redisOrder;
    }

    @PostMapping("/redis/update/{id}")
    public RedisOrder updateOrder(@PathVariable Long id, @RequestBody String status){
        RedisOrder update = redisService.update(id, status);
        return update;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/redis/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        redisService.delete(id);
        return "주문건 삭제 성공";
    }




}
