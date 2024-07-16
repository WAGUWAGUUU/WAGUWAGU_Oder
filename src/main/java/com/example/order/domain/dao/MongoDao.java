package com.example.order.domain.dao;


import com.example.order.domain.entity.Order;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoDao implements MongoDaoImpl{

    private final MongoTemplate mongoTemplate;

    public List<Order> selectByDate(Long id,LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int pageSize) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("orderCreatedAt").gte(startDate).lt(endDate).and("isDeleted").is(false));
        query.skip(pageNumber * pageSize).limit(pageSize);

        return mongoTemplate.find(query, Order.class);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").is(customerId).and("isDeleted").is(false));

        return mongoTemplate.find(query, Order.class);
    }

    @Override
    public List<Order> findByOwnerId(Long ownerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(ownerId).and("isDeleted").is(false));


        return mongoTemplate.find(query, Order.class);
    }

    public List<Order> findById(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("isDeleted").is(false));

        return mongoTemplate.find(query, Order.class);

    }

    @Override
    public void save(Order order) {
        mongoTemplate.save(order);
    }

    @Override
    public UpdateResult delete(Long id) {

        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("isDeleted", true);

        return  mongoTemplate.updateMulti(query, update, Order.class);
    }
}
