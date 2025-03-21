package com.example.newsfeed.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long userid;
    private final String email;
    private final String username;
    private final String address;
    private final LocalDateTime createdAt;
}
