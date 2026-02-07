package com.danilodps.kafkaconsumer.domain.record.received;

import lombok.Builder;

@Builder
public record UserResponse(String userId, String name, String lastName) { }
