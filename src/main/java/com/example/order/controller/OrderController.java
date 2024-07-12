package com.example.order.controller;


import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.dto.MongoDto;
import com.example.order.domain.dto.RedisDto;
import com.example.order.service.MongoService;
import com.example.order.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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



    @PostMapping("redis")
    public void save1(@RequestBody RedisDto redisDto){

        redisService.save(redisDto);
    }

    @GetMapping("redis")
    public RedisOrder get1(@RequestBody RedisDto redisDto){

        RedisOrder redisOrder = redisService.get(redisDto);

        return redisOrder ;
    }

}
