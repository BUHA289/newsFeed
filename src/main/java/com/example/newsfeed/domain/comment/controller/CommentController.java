package com.example.newsfeed.domain.comment.controller;

import com.example.newsfeed.domain.comment.dto.request.CommentRequestDto;
import com.example.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.domain.comment.service.CommentService;
import com.example.newsfeed.domain.common.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<MessageResponseDto> write(@RequestBody CommentRequestDto dto) {
        commentService.writeComment(dto);
        return ResponseEntity.ok(new MessageResponseDto("댓글 작성 성공"));
    }

    // 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestParam Long userId,
                                                            @RequestBody String newContents) {
        commentService.updateComment(commentId, userId, newContents);
        return ResponseEntity.ok(new MessageResponseDto("댓글 수정 성공"));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long commentId,
                                                            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(new MessageResponseDto("댓글 삭제 성공"));
    }
}
