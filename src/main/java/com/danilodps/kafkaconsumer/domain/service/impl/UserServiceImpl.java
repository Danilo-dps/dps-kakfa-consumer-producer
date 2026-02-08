package com.danilodps.kafkaconsumer.domain.service.impl;

import com.danilodps.kafkaconsumer.application.exception.BusinessException;
import com.danilodps.kafkaconsumer.domain.adapter.UserResponse2UserEntity;
import com.danilodps.kafkaconsumer.domain.entity.UserEntity;
import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;
import com.danilodps.kafkaconsumer.domain.repository.UserEntityRepository;
import com.danilodps.kafkaconsumer.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    /**
     * Para testar o RetryTopic, basta fazer o seguinte ajuste:
     *  ===========================================================
     *             throw new RuntimeException("teste create retry");
     * //            log.info("Criando usuário...");
     * //            UserEntity userEntity = UserResponse2UserEntity.convert(userResponse);
     * //            userEntityRepository.saveAndFlush(userEntity);
     * //            log.info("Usuário criado!");
     *  ===========================================================
     *  assim sempre garante uma exception sendo lançada
     */
    @Override
    @Transactional
    public void create(UserResponse userResponse) {
        try {
            log.info("Criando usuário...");
            UserEntity userEntity = UserResponse2UserEntity.convert(userResponse);
            userEntityRepository.saveAndFlush(userEntity);
            log.info("Usuário criado!");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
        }
    }

    /**
     * Para testar o RetryTopic, basta fazer o seguinte ajuste:
     * ===========================================================
     *            throw new RuntimeException("teste update retry");
     * //            log.info("Atualizando usuário...");
     * //            String fullName = userResponse.name() + " " + userResponse.lastName();
     * //            userEntityRepository.update(userResponse.userId(), fullName, LocalDateTime.now());
     * //            log.info("Usuário atualizado!");
     * ===========================================================
     * assim sempre garante uma exception sendo lançada
     */

    @Override
    @Transactional
    public void update(UserResponse userResponse) {
        try {
            log.info("Atualizando usuário...");
            String fullName = userResponse.name() + " " + userResponse.lastName();
            userEntityRepository.update(userResponse.userId(), fullName, LocalDateTime.now());
            log.info("Usuário atualizado!");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
        }
    }

    /**
     * Para testar o RetryTopic, basta fazer o seguinte ajuste:
     * ===========================================================
     * throw new RuntimeException("teste delete retry");
     * //            log.info("Deletando usuário...");
     * //            userEntityRepository.delete(userResponse.userId());
     * //            log.info("Usuário deletado!");
     * ===========================================================
     * assim sempre garante uma exception sendo lançada
     */
    @Override
    @Transactional
    public void delete(UserResponse userResponse) {
        try {
            log.info("Deletando usuário...");
            userEntityRepository.delete(userResponse.userId());
            log.info("Usuário deletado!");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
        }
    }
}
