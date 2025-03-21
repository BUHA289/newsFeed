package com.example.newsfeed.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    private String email;
    private String password;
    private String username;
    private String address;

}
