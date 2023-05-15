package com.example.demo;

import com.example.demo.test.AbstractEvent;
import com.example.demo.test.TestEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.listener.annotation.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(TestEvent event) {
        System.out.println(event);
    }
}

