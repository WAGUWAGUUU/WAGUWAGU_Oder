package com.example.order.controller;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;
import com.example.order.service.MongoService;
import com.example.order.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final MongoService mongoService;
    private final RedisService redisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/history")
    public String orderSave(@RequestBody UserRequest userRequest) {
        mongoService.save(userRequest);
        redisService.save(userRequest);
        return "주문내역 저장 성공";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{requestId}/history")
    public List<Order> selectByDate(@PathVariable Long requestId,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestParam int pageNumber
                                    ){

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

    @PostMapping("/update/{orderId}")
    public Order updateState(@PathVariable Long orderId , @RequestBody String status){
        Order update = mongoService.update(orderId, status);
        return update;
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{orderId}")
    public String delete(@PathVariable Long orderId) {
        mongoService.delete(orderId);
        return "주문건 삭제 성공";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public String createOrder(@RequestBody UserRequest userRequest) {
        redisService.save(userRequest);
        return "주무건 생성 성공";
    }


    @GetMapping("/{requestId}")
    public  List<RedisOrder> getOrder1(@PathVariable Long requestId) {
        List<RedisOrder> redisOrders = (List<RedisOrder>) redisService.get(requestId);
        System.out.println(redisOrders.stream().toList());
        return redisOrders;
    }

    @PostMapping("/request/{id}")
    public RedisOrder updateOrder(@PathVariable UUID id, @RequestBody String status){
        System.out.println("컨트롤 반응");
        System.out.println(status);
        RedisOrder update = redisService.update(id, status);
        return update;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        redisService.delete(id);
        return "주문건 삭제 성공";
    }




}
