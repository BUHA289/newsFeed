package com.example.newsfeed.domain.comment.service;

import com.example.newsfeed.domain.comment.dto.request.CommentRequestDto;
import com.example.newsfeed.domain.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.domain.comment.entity.Comment;
import com.example.newsfeed.domain.comment.repository.CommentRepository;
import com.example.newsfeed.domain.post.entity.Post;
import com.example.newsfeed.domain.post.repository.PostRepository;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void writeComment(CommentRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(() -> new RuntimeException("게시글 없음"));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("유저 없음"));

        Comment comment = new Comment(post, user, dto.getContents());
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 없음"));

        return commentRepository.findAllByPost(post).stream()
                .map(c -> new CommentResponseDto(
                        c.getId(),
                        c.getUser().getId(),
                        c.getUser().getUsername(),
                        c.getContents()
                )).toList();
    }

    @Transactional
    public void updateComment(Long commentId, Long userId, String newContents) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.updateContents(newContents);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    // 게시글 삭제 시 호출
    @Transactional
    public void deleteAllByPost(Post post) {
        commentRepository.deleteAllByPost(post);
    }
}
