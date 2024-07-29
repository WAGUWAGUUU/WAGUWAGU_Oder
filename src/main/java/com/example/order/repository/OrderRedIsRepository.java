package com.example.order.repository;

import com.example.order.domain.entity.Order;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRedIsRepository extends ListCrudRepository<Order, String> {


}
