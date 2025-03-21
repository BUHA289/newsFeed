package com.example.newsfeed.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginResponseDto {
    private final Long id;
    private final String email;

    private final String username;


}
