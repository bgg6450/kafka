package com.example.demo;

import com.example.demo.test.TestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

// TODO: 2023/05/15 abstract 클래스로 인자 넣어줄 것
@Service
public class KafkaProducer<Object> {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void pub(String topic, Object event) {
        // topic, event 전달. key 추가를 원하면 2nd 인자에 key 추가
        // default topic 에 event 만 전달하려면 sendDefault() 사용
        kafkaTemplate.send(topic, event);
    }
}

