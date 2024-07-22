package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final RedisService redisService;
    private final MongoService mongoService;

    @Override
    public void createOrder(UserRequest userRequest) {
        redisService.save(userRequest);
        mongoService.save(userRequest);
    }

    @Override
    public RedisOrder upDateOrder(Long id, String status) {
        String hexString = Long.toHexString(id);
        String paddedHexString = String.format("%024x", Long.parseLong(hexString));
        ObjectId objectId = new ObjectId(paddedHexString);

        RedisOrder update = redisService.update(id, status);
        mongoService.update(objectId,status);

        return update;
    }
}
