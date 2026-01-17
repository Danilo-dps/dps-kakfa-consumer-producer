package com.danilodps.kafkaconsumer.service.impl;

import com.danilodps.kafkaconsumer.entity.UserEntity;
import com.danilodps.kafkaconsumer.record.response.UserResponse;
import com.danilodps.kafkaconsumer.repository.UserEntityRepository;
import com.danilodps.kafkaconsumer.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    @Transactional
    public void create(UserResponse userResponse) {
        log.info("Criando usuário...");

        UserEntity userEntity = UserEntity.builder()
                .fullName((userResponse.name() + " " + userResponse.lastName()))
                .createdAt(LocalDateTime.now())
                .build();

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário criado!");
    }
}
