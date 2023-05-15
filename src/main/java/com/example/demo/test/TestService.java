package com.example.demo.test;

import com.example.demo.KafkaConsumer;
import com.example.demo.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TestService {
    private final KafkaConsumer kafkaConsumer;
    private final KafkaProducer<Object> kafkaProducer;

    public void pubMessage() {
        TestEvent event = new TestEvent("testing...attention plz");
        kafkaProducer.pub("test", new TestEvent("testing...attention plz"));
        log.info("pub: {}", event.getMessage());
    }

    public void subMessage(TestEvent event) {
        kafkaConsumer.listen(event);
    }
}
