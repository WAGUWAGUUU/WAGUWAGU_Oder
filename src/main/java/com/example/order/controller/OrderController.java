package com.example.order.controller;


import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.MongoDto;
import com.example.order.domain.dto.RedisDto;
import com.example.order.service.MongoService;
import com.example.order.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/Order")
@RequiredArgsConstructor
public class OrderController {

    private final MongoService mongoService;
    private final RedisService redisService;

    @PostMapping("mongo")
    public void save(@RequestBody MongoDto mongoDto){

        mongoService.save(mongoDto);
    }
    @GetMapping("mongo")
    public Order get(){
        Order order = mongoService.get();

        return order;
    }

    @PostMapping("/redis")
    public ResponseEntity<RedisOrder> createOrder(@RequestBody RedisDto redisDto) {
        redisService.save(redisDto);
        return ResponseEntity.ok().build(); // Ensure response is returned correctly
    }

    @GetMapping("/{id}")
    public ResponseEntity<RedisOrder> getOrder(@PathVariable long id) {
        Optional<RedisOrder> order = redisService.get(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
