package com.example.newsfeed.domain.post.controller;

import com.example.newsfeed.domain.common.dto.response.MessageResponseDto;
import com.example.newsfeed.domain.post.dto.request.PostCreateRequestDto;
import com.example.newsfeed.domain.post.dto.request.PostDeleteRequestDto;
import com.example.newsfeed.domain.post.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.domain.post.dto.response.PostCreateResponseDto;
import com.example.newsfeed.domain.post.dto.response.PostResponseDto;
import com.example.newsfeed.domain.post.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public PostCreateResponseDto createPost (@PathVariable Long id, @RequestBody PostCreateRequestDto dto) {
        return postService.createPost(id, dto);
    }

    @GetMapping("/posts/findAll")
    public Page<PostResponseDto> findAll (
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return postService.findAll(page, size);
    }

    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost (@PathVariable Long postId,
                                             @RequestBody PostUpdateRequestDto dto) {
        return postService.updatePost(postId, dto);
    }

    @PostMapping("/posts/{userId}/delete")
    public ResponseEntity<MessageResponseDto> delete (@PathVariable Long userId,
                                      @RequestBody PostDeleteRequestDto dto) {
        postService.deleteById(userId, dto);
        return ResponseEntity.ok(new MessageResponseDto("게시물 삭제에 성공했습니다."));
    }

    @GetMapping("/posts/following/{userId}/search")
    public ResponseEntity<Page<PostResponseDto>> searchPostsByFollowingAndPeriod(
            @PathVariable Long userId,
            @RequestParam String startDate,   // yyyy.MM.dd
            @RequestParam String endDate,     // yyyy.MM.dd
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> response = postService.findPostsByFollowingAndPeriod(
                userId, startDate, endDate, page, size);
        return ResponseEntity.ok(response);
    }
}
