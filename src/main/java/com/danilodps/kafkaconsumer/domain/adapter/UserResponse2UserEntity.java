package com.danilodps.kafkaconsumer.domain.adapter;

import com.danilodps.kafkaconsumer.domain.entity.UserEntity;
import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;

import java.time.LocalDateTime;

public class UserResponse2UserEntity {

    private UserResponse2UserEntity(){}

    /**
     * UserEntity usa a referência userId do DTO que o consumer recebe,
     * a proposta é ter uma referência da entidade criada no serviço produtor da mensagem
     * @param userResponse
     * @return UserEntity
     */
    public static UserEntity convert(UserResponse userResponse){
        String fullName = userResponse.name() + " " + userResponse.lastName();
        return UserEntity.builder()
                .userId(userResponse.userId())
                .fullName(fullName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
