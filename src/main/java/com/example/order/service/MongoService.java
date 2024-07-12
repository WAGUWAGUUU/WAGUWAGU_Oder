package com.example.order.service;

import com.example.order.domain.entity.Order;
import com.example.order.domain.dto.MongoDto;

public interface MongoService {
     void save(MongoDto mongoDto);
     Order get();


}
