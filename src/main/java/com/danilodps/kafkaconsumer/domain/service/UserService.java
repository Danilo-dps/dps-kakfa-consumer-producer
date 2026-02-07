package com.danilodps.kafkaconsumer.domain.service;

import com.danilodps.kafkaconsumer.domain.record.received.UserResponse;

public interface UserService {

    void create(UserResponse userResponse);
    void update(UserResponse userResponse);
    void delete(UserResponse userResponse);
}
