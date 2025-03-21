package com.example.newsfeed.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostDeleteRequestDto {
    private Long postId;
    private String password;
}
