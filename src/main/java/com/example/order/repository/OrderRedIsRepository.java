package com.example.order.repository;

import com.example.order.domain.entity.RedisOrder;
import org.springframework.data.repository.ListCrudRepository;


public interface OrderRedIsRepository extends ListCrudRepository<RedisOrder, String> {



}
