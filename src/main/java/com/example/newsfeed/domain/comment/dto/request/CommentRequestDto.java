package com.example.newsfeed.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private Long userId;
    private String contents;
}
