package com.example.newsfeed.domain.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long postId;
    private final Long userId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;

    public PostResponseDto(Long postId, Long userId, String title, String contents, LocalDateTime createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
    }
}
