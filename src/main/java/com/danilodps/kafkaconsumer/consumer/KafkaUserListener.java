package com.danilodps.kafkaconsumer.consumer;

import com.danilodps.kafkaconsumer.record.response.UserResponse;
import com.danilodps.kafkaconsumer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaUserListener {

    private final UserService userService;

    @KafkaListener(groupId = "consumer-group", topics = "topic-user-v1", containerFactory = "listenerContainerFactory")
    public void consumer(UserResponse userResponse){
        try {
            log.info("name: {}, lastName: {}", userResponse.name(), userResponse.lastName());
            userService.create(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}