package com.danilodps.kafkaconsumer.record.response;

import lombok.Builder;

@Builder
public record UserResponse(String userId, String name, String lastName, String createdAt) { }
