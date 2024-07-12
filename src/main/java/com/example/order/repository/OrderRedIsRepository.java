package com.example.order.repository;

import com.example.order.domain.entity.RedisOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRedIsRepository extends CrudRepository<RedisOrder,String> {


}
