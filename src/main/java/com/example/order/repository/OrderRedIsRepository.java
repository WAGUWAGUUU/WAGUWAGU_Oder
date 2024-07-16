package com.example.order.repository;

import com.example.order.domain.dao.RedisDaoImpl;
import com.example.order.domain.entity.RedisOrder;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRedIsRepository extends CrudRepository<RedisOrder, String> {


    void delete(Optional<RedisOrder> byId);
    @Query("FT.SEARCH idx:id \"@price==id\"")
    RedisOrder findById(Long id);

}
