package com.example.newsfeed.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostCreateRequestDto {
    private String title;
    private String contents;

}
