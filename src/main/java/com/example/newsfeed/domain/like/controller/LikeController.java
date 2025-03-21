package com.example.newsfeed.domain.like.controller;

import com.example.newsfeed.domain.common.dto.response.MessageResponseDto;
import com.example.newsfeed.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<MessageResponseDto> likePost(@RequestParam Long userId, @PathVariable Long postId) {
        likeService.likePost(userId, postId);
        return ResponseEntity.ok(new MessageResponseDto("게시물 좋아요 완료"));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<MessageResponseDto> cancelPost(@RequestParam Long userId, @PathVariable Long postId) {
        likeService.cancelLikePost(userId, postId);
        return ResponseEntity.ok(new MessageResponseDto("게시물 좋아요 취소"));
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<MessageResponseDto> likeComment(@RequestParam Long userId, @PathVariable Long commentId) {
        likeService.likeComment(userId, commentId);
        return ResponseEntity.ok(new MessageResponseDto("댓글 좋아요 완료"));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<MessageResponseDto> cancelComment(@RequestParam Long userId, @PathVariable Long commentId) {
        likeService.cancelLikeComment(userId, commentId);
        return ResponseEntity.ok(new MessageResponseDto("댓글 좋아요 취소"));
    }
}
