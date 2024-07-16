package com.example.order.controller;


import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.MongoDto;
import com.example.order.domain.dto.RedisDto;
import com.example.order.domain.request.StoreRequest;
import com.example.order.service.MongoService;
import com.example.order.service.RedisService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Order")
@RequiredArgsConstructor
public class OrderController {

    private final MongoService mongoService;
    private final RedisService redisService;

    public void orderSave(@RequestBody MongoDto mongoDto){
        mongoService.save(mongoDto);
    }

    @GetMapping("/mongo/{requestId}/history/{startDate}/{endDate}")
    public List<Order> selectByDate(@PathVariable Long requestId,LocalDateTime startDate,LocalDateTime endDate,
                                    @RequestBody StoreRequest storeRequest){

        List<Order> orders = mongoService.selectByDate(requestId,startDate,endDate,storeRequest.pageNumber());
        return orders;
    }

    @GetMapping("/mongo/{ownerId}/history")
    public List<Order> selectByOwnerAll(@PathVariable Long ownerId){

        List<Order> orders = mongoService.findByCustomerId(ownerId);
        return orders;
    }

    @GetMapping("/mongo/{consumerId}/history")
    public List<Order> selectByConsumerAll(@PathVariable Long consumerId){

        List<Order> orders = mongoService.findByCustomerId(consumerId);
        return orders;
    }

    @PostMapping("/mongo/{id}")
    public UpdateResult delete(@PathVariable Long mongoId) {
        UpdateResult delete = mongoService.delete(mongoId);
        return delete;
    }

    @PostMapping("/redis")
    public ResponseEntity<RedisOrder> createOrder(@RequestBody RedisDto redisDto) {
        redisService.save(redisDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redis/{id}")
    public ResponseEntity<RedisOrder> getOrder1(@PathVariable Long id) {
        Optional<RedisOrder> order = redisService.get(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
