package dps.kafka.consumer;

import com.danilodps.kafkaconsumer.domain.adapter.UserResponse2UserEntity;
import com.danilodps.kafkaconsumer.domain.entity.UserEntity;
import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;
import com.danilodps.kafkaconsumer.domain.repository.UserEntityRepository;
import com.danilodps.kafkaconsumer.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserEntityRepository userEntityRepository;

    @InjectMocks
    UserServiceImpl userService;

    UserEntity userEntity;
    UserResponse userResponse;

    @BeforeEach
    void setup(){

        userEntity = UserEntity.builder()
                .userId(("871982fe-57a2-4eab-af9f-97e880ee2cbf"))
                .fullName("Danilo" + " Pereira")
                .createdAt(LocalDateTime.now())
                .build();

        userResponse = UserResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getFullName())
                .build();

        userService = new UserServiceImpl(userEntityRepository);
    }

    @Test
    void testCreateAUser() {

        when(userEntityRepository.saveAndFlush(any(UserEntity.class))).thenReturn(userEntity);

        try (MockedStatic<UserResponse2UserEntity> mockedStatic = mockStatic(UserResponse2UserEntity.class)) {
            mockedStatic.when(() -> UserResponse2UserEntity.convert(any(UserResponse.class)))
                    .thenReturn(userEntity);
            userService.create(userResponse);
            mockedStatic.verify(() -> UserResponse2UserEntity.convert(any(UserResponse.class)));
        }

        verify(userEntityRepository, atLeastOnce()).saveAndFlush(any(UserEntity.class));
        Assertions.assertEquals("Danilo Pereira", userEntity.getFullName());
        Assertions.assertNotNull(userEntity.getCreatedAt());
    }

    @Test
    void testUpdateAUser() {

        userService.update(userResponse);

        verify(userEntityRepository, times(1)).update(
                eq(userResponse.userId()),
                anyString(),
                any(LocalDateTime.class)
        );
    }

    @Test
    void testDeleteAUser() {

        userService.delete(userResponse);

        verify(userEntityRepository, times(1)).delete(userResponse.userId());
    }
}