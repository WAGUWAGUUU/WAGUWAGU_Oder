package com.example.order.repository;

import com.example.order.domain.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order, UUID> {
}
