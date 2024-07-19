package com.example.order.service;

import com.example.order.domain.entity.Order;

import com.example.order.kafka.dto.KafkaDeliveryDTO;
import com.example.order.kafka.dto.KafkaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final KafkaTemplate<String, KafkaStatus<KafkaDeliveryDTO>> kafkaTemplate;

    @Override
    public void statusTracking() {

    }


    @Override
    public Order history() {
        return null;
    }

    @Override
    public void deliveryKafkaEvent() {

    }



}
