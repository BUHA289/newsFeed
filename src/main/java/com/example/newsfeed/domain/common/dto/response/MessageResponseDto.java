package com.example.newsfeed.domain.common.dto.response;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}
