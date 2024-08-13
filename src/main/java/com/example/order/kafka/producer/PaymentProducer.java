package com.example.order.kafka.producer;

import com.example.order.kafka.dto.KafkaPaymentDto;
import com.example.order.kafka.dto.KafkaStatus;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private final KafkaTemplate<String, KafkaStatus<KafkaPaymentDto>> kafkaTemplate;
    private final String TOPIC = "payment";
    @Bean
    private NewTopic newTopicForPayment() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    public void send(KafkaPaymentDto kafkaPaymentDto, String status) {
        KafkaStatus<KafkaPaymentDto> kafkaStatus = new KafkaStatus<>(kafkaPaymentDto, status);
        kafkaTemplate.send(TOPIC, kafkaStatus);
    }
}
