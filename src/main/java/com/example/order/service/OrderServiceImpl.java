package com.example.order.service;

//import com.example.order.api.ApiVerification;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.StoreRequest;
import com.example.order.domain.response.StoreResponse;
import com.example.order.repository.OrderRedIsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MongoTemplate mongoTemplate;
    private final OrderRedIsRepository redIsRepository;
//    private final ApiVerification apiVerification;


//    @Override
//    public boolean verification() {
//
//        Optional<RedisOrder> byId = redIsRepository.findById(String.valueOf(2L));
//
//        apiVerification.send();
//        StoreResponse storeResponse = apiVerification.get();
//        if( storeResponse.AvailableForSale()){
//             return true;
//        }
//             return false;
//    }






    @Override
    public void statusTracking() {




        String status="배달완료";

        Optional<RedisOrder> byId = redIsRepository.findById("8bd37582-2aeb-4213-9dfa-aaa432fb9983");

        RedisOrder redisOrder = RedisOrder.builder()
                .id(Long.valueOf("8bd37582-2aeb-4213-9dfa-aaa432fb9983"))
                .orderState(status)
                .orderCreatedAt(LocalDateTime.now())
                .build();

        redIsRepository.save(redisOrder);

    }


    @Override
    public Order history() {
        return null;
    }


}
