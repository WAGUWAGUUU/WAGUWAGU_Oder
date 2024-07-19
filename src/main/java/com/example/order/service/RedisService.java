package com.example.order.service;

import com.example.order.domain.entity.RedisOrder;
import com.example.order.domain.request.UserRequest;

public interface RedisService {

   void save(UserRequest userRequest);
   RedisOrder get(Long id);
   RedisOrder update(Long id,String state);
   void delete(Long id);

}
