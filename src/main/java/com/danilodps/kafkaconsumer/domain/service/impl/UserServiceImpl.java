package com.danilodps.kafkaconsumer.domain.service.impl;

import com.danilodps.kafkaconsumer.domain.adapter.UserResponse2UserEntity;
import com.danilodps.kafkaconsumer.domain.entity.UserEntity;
import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;
import com.danilodps.kafkaconsumer.domain.repository.UserEntityRepository;
import com.danilodps.kafkaconsumer.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    @Transactional
    public void create(UserResponse userResponse) {
        log.info("Criando usuário...");

        UserEntity userEntity = UserResponse2UserEntity.convert(userResponse);

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário criado!");
    }

    @Override
    @Transactional
    public void update(UserResponse userResponse) {
        log.info("Atualizando usuário...");

        String fullName = userResponse.name() + " " + userResponse.lastName();
        userEntityRepository.update(userResponse.userId(), fullName, LocalDateTime.now());

        log.info("Usuário atualizado!");
    }

    @Override
    @Transactional
    public void delete(UserResponse userResponse) {
        log.info("Deletando usuário...");

        userEntityRepository.delete(userResponse.userId());
        log.info("Usuário deletado!");
    }
}
