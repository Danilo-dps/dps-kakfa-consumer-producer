package com.danilodps.kafkaconsumer.application.consumer;

import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;
import com.danilodps.kafkaconsumer.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserService userService;

    @KafkaListener(
            groupId = "consumer-group",
            topics = {"topic-user-created"},
            containerFactory = "listenerContainerFactory"
    )
    public void consumerCreate(UserResponse userResponse){
        try {
            userService.create(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(
            groupId = "consumer-group",
            topics = {"topic-user-updated"},
            containerFactory = "listenerContainerFactory"
    )
    public void consumerUpdate(UserResponse userResponse){
        try {
            log.info("Solicitação de atualização");
            userService.update(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(
            groupId = "consumer-group",
            topics = {"topic-user-deleted"},
            containerFactory = "listenerContainerFactory"
    )
    public void consumerDelete(UserResponse userResponse){
        try {
            log.info("Solicitação de exclusão");
            userService.delete(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}