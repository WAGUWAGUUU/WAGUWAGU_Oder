package com.example.order.kafka.producer;

import com.example.order.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Configuration
public class KafkaProducer {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("order-topic", 1, (short) 1);
    }

    private final KafkaTemplate<String, KafkaStatus<KafkaDeliveryDTO>> kafkaDeliveryTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaCartDTO>> kafkaCartTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaSalesDTO>> kafkaSaleTemplate;
    private final KafkaTemplate<String, KafkaStatus<KafkaPushReqDTO>> kafkaPushReqTemplate;

    public void KafkaDeliverySend(KafkaDeliveryDTO kafkaDeliveryDTO, String status) {
        KafkaStatus<KafkaDeliveryDTO> kafkaStatus = new KafkaStatus<>(kafkaDeliveryDTO, status);
        kafkaDeliveryTemplate.send("order-topic", kafkaStatus);
    }

    public void KafkaCartSend(KafkaCartDTO kafkaCartDTO, String status) {
        KafkaStatus<KafkaCartDTO> kafkaStatus = new KafkaStatus<>(kafkaCartDTO, status);
        kafkaCartTemplate.send("order-topic", kafkaStatus);
    }

    public void KafkaSalesSend(KafkaSalesDTO kafkaSalesDTO, String status) {
        KafkaStatus<KafkaSalesDTO> kafkaStatus = new KafkaStatus<>(kafkaSalesDTO, status);
        kafkaSaleTemplate.send("order-topic", kafkaStatus);
    }

    public void KafkaPushReqSend(KafkaPushReqDTO kafkaPushReqDTO, String status) {
        KafkaStatus<KafkaPushReqDTO> kafkaStatus = new KafkaStatus<>(kafkaPushReqDTO, status);
        kafkaPushReqTemplate.send("order-topic", kafkaStatus);
    }

}
