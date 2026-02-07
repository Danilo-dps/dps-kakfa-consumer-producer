package com.danilodps.kafkaconsumer.application.config;

import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) { this.kafkaProperties = kafkaProperties;}

    @Bean
    ConsumerFactory<String, UserResponse> consumerFactory(){
        Map<String, Object> configConsumerFactory = new HashMap<>();
        configConsumerFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        configConsumerFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        configConsumerFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configConsumerFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        configConsumerFactory.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
        configConsumerFactory.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, UserResponse.class.getName());
        return new DefaultKafkaConsumerFactory<>(configConsumerFactory);
    }

    @Bean("listenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<String, UserResponse> listenerContainerFactory(
            ConsumerFactory<String, UserResponse> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, UserResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

}

