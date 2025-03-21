package com.example.newsfeed.domain.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequestDto {
    private Long userId;
    private String title;
    private String contents;

}
