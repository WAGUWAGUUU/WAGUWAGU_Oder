package com.example.order.domain.dao;

import com.example.order.domain.entity.OrderHistory;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MongoDaoImpl implements MongoDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<OrderHistory> selectByStoreDate(Long storeId, Timestamp startDate, Timestamp endDate,  Long offset, int pageSize) {
        Query query = new Query();
        query.addCriteria(Criteria.where("storeId").is(storeId)
                .and("CREATED").gte(startDate).lt(endDate)
                .and("isDeleted").is(false));
        query.skip(offset * pageSize).limit(pageSize);
        return mongoTemplate.find(query, OrderHistory.class);
    }

    @Override
    public List<OrderHistory> findByCustomerId(Long customerId, Long offset) {
//        int pageSize = 10;
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").is(customerId).and("isDeleted").is(false));
//        query.skip(offset).limit(pageSize);
        List<OrderHistory> orderHistories = mongoTemplate.find(query, OrderHistory.class);
        return orderHistories;
    }

    @Override
    public void save(OrderHistory orderHistory) {
        mongoTemplate.save(orderHistory);
        log.debug("MongoDAO");
    }

    @Override
    public UpdateResult delete(Long id) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("isDeleted", true);
        return  mongoTemplate.updateMulti(query, update, OrderHistory.class);
    }

    //    @Override
//    public List<OrderHistory> findByStoreId(Long ownerId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("ownerId").is(ownerId).and("isDeleted").is(false));
//        List<OrderHistory> orderHistories = mongoTemplate.find(query, OrderHistory.class);
//        return orderHistories;
//    }

    //    @Override
//    public List<OrderHistory> selectByCustomerIdDate(Long customerId, Timestamp startDate, Timestamp endDate, int pageNumber, int pageSize) {
//
//        Query query = new Query();
//        query.addCriteria(Criteria.where("customerId").is(customerId)
//                .and("CREATED").gte(startDate).lt(endDate)
//                .and("isDeleted").is(false));
//        query.skip((long) pageNumber * pageSize).limit(pageSize);
//        return mongoTemplate.find(query, OrderHistory.class);
//    }

}
