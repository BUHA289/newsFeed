package com.example.newsfeed.domain.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {
    private final Long commentId;
    private final Long userId;
    private final String username;
    private final String contents;
}
