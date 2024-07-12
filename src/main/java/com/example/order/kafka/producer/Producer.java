package com.example.order.kafka.producer;

import com.example.order.kafka.dto.KafkaEventDto;
import com.example.order.kafka.dto.KafkaStatus;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;


@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String,KafkaStatus<KafkaEventDto>> kafkaTemplate;

    String topic ="";

    @Bean
    private NewTopic newTopic(){
        return new NewTopic(topic, 1, (short) 1);
    }

    public void send(KafkaEventDto kafkaEventDto, String status, String topic){
        KafkaStatus<KafkaEventDto> kafkaStatus = new KafkaStatus<>(kafkaEventDto, status);
        kafkaTemplate.send(topic,kafkaStatus);
    }

}
