package com.example.order.domain.dao;


import com.example.order.domain.entity.OrderHistory;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoDao implements MongoDaoImpl{

    private final MongoTemplate mongoTemplate;

    public List<OrderHistory> selectByDate(Long id, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize) {

        LocalTime customTime = LocalTime.of(0, 0, 0);

        LocalDateTime startDateLocalDateTime = startDate.atTime(customTime);
        LocalDateTime endDateLocalDateTime = endDate.atTime(customTime);
        Query query = new Query();
        query.addCriteria(Criteria.where("orderCreatedAt").gte(startDate).lt(endDate).and("isDeleted").is(false));
        query.skip(pageNumber * pageSize).limit(pageSize);

        return mongoTemplate.find(query, OrderHistory.class);
    }

    @Override
    public List<OrderHistory> findByCustomerId(Long customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").is(customerId).and("isDeleted").is(false));
        List<OrderHistory> orderHistories = mongoTemplate.find(query, OrderHistory.class);
        return orderHistories;
    }


    @Override
    public List<OrderHistory> findByOwnerId(Long ownerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(ownerId).and("isDeleted").is(false));
        List<OrderHistory> orderHistories = mongoTemplate.find(query, OrderHistory.class);
        return orderHistories;
    }


    @Override
    public void save(OrderHistory orderHistory) {
        mongoTemplate.save(orderHistory);
        System.out.println("MongoDAO");
    }

    @Override
    public OrderHistory update(Long id, String state) {

        Query query = new Query(Criteria.where("_id").is(id));
        OrderHistory orderHistory = mongoTemplate.findOne(query, OrderHistory.class);
        orderHistory.setOrderState(state, LocalDateTime.now());

        mongoTemplate.save(orderHistory);

        return orderHistory;
    }

    @Override
    public UpdateResult delete(Long id) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("isDeleted", true);
        return  mongoTemplate.updateMulti(query, update, OrderHistory.class);
    }
}
