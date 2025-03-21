package com.example.newsfeed.domain.follwer.controller;

import com.example.newsfeed.domain.common.dto.response.MessageResponseDto;
import com.example.newsfeed.domain.follwer.dto.request.FollowingRequestDto;
import com.example.newsfeed.domain.follwer.service.FollowService;
import com.example.newsfeed.domain.post.dto.response.PostResponseDto;
import com.example.newsfeed.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final PostService postService;

    //  팔로잉 유저 추가
    @PostMapping("/users/{userId}/follow")
    public MessageResponseDto addFollowing(
            @PathVariable Long userId,
            @RequestBody FollowingRequestDto requestDto
    ) {
        followService.addFollowing(userId, requestDto.getFollowingUserId());
        return new MessageResponseDto("팔로우에 성공했습니다.");
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<Page<PostResponseDto>> findPostByFollowing(
            @PathVariable Long userId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<PostResponseDto> response = postService.findPostByFollowing(userId, page, size);
        return ResponseEntity.ok(response);
    }
}
