package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;

import java.util.List;
import java.util.UUID;

public interface RedisService {

   void save(UserRequest userRequest);
   List<RedisOrder> get(Long requestId);
   RedisOrder update(UUID id, String state);
   void delete(Long id);

}
