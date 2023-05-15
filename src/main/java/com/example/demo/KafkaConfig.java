package com.example.demo;

import com.example.demo.test.AbstractEvent;
import com.example.demo.test.TestEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    /**
     * [ 사용 예시 ]
     *
     * @Autowired
     * private KafkaProducer<Event> eventProducer;
     *
     * @Autowired
     * private KafkaProducer<AnotherEvent> anotherEventProducer;
     *
     * public void sendEvents() {
     *     eventProducer.pub("event-topic", new Event());
     *     anotherEventProducer.pub("another-event-topic", new AnotherEvent());
     * }
     **/

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TestEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(5);  동시에 실행할 Kafka Consumer 인스턴스 수 설정
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TestEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

        // Creating ErrorHandlingDeserializer for key
        ErrorHandlingDeserializer<String> keyDeserializer = new ErrorHandlingDeserializer<>(new StringDeserializer());

        // Creating ErrorHandlingDeserializer for value
        JsonDeserializer<TestEvent> jsonDeserializer = new JsonDeserializer<>(TestEvent.class);
        jsonDeserializer.addTrustedPackages("*");   // 현 설정: 모든 패키지를 신뢰. 개별 적용하려면 하나씩 추가 필요
//        jsonDeserializer.setUseTypeMapperForKey(true);  // true 로 설정하면 역직렬화한 객체 타입이 Object 대신 헤더에 저장된 클래스 타입 정보가 됨
        jsonDeserializer.setTypeMapper(new DefaultJackson2JavaTypeMapper()); // 기본 유형 매퍼 설정

        ErrorHandlingDeserializer<TestEvent> valueDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                keyDeserializer,
                valueDeserializer
        );
    }
}

