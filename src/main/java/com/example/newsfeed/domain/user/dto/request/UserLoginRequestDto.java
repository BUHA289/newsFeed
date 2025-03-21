package com.example.newsfeed.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String email;
    private String password;
}
