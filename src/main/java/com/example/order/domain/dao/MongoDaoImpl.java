package com.example.order.domain.dao;

import com.example.order.domain.entity.OrderHistory;
import com.mongodb.client.result.UpdateResult;
import java.sql.Timestamp;
import java.util.List;

public interface MongoDaoImpl{

//    List<OrderHistory> findByStoreId(Long ownerId);
//    가게 유저와 1:N 관계로 많은 데이터가 생성될 것으로 예상이 되는 부분으로 검색 조건에 단일 가게 아이디로 찾기를 한다면 서버와 데이터 베이스에 부담이 될 것으로 예상되어 주석처리 하였습니다.
//    List<OrderHistory> selectByCustomerIdDate(Long customerId, Timestamp startDate, Timestamp endDate, int pageNumber, int pageSize);
//    유저 경우 개인 주문건이 아무리 많아도 1인 유저가 주문할 데이터는 한정이 될 것으로 유저 아이디로 전체를 찾는 것으로 구현 이후 해당 로직은 페이징처리로 서부 부하와 데이터베이스 부하 처리
    List<OrderHistory> selectByStoreDate(Long storeId, Timestamp startDate, Timestamp endDate,  Long offset, int pageSize);
    List<OrderHistory> findByCustomerId(Long customerId, Long offset);
    void save(OrderHistory orderHistory);
    UpdateResult delete(Long id);
}
