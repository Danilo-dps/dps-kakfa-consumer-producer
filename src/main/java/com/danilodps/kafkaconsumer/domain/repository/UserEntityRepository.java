package com.danilodps.kafkaconsumer.domain.repository;

import com.danilodps.kafkaconsumer.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = """
                UPDATE TB_USER_FULL_NAME
                SET 
                FULL_NAME = :fullName,
                UPDATED_AT = :updatedAt
                WHERE USER_ID = :userId
                """)
    void update(
            @Param("userId") String userId,
            @Param("fullName") String fullName,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = """
                    DELETE FROM TB_USER_FULL_NAME WHERE USER_ID = :userId
                    """)
    void delete(@Param("userId") String userId);
}
