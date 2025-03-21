package com.example.newsfeed.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserSignUpResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String username;
    private final String address;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
