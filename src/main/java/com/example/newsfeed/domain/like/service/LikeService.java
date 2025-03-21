package com.example.newsfeed.domain.like.service;

import com.example.newsfeed.domain.comment.entity.Comment;
import com.example.newsfeed.domain.comment.repository.CommentRepository;
import com.example.newsfeed.domain.like.entity.Like;
import com.example.newsfeed.domain.like.repository.LikeRepository;
import com.example.newsfeed.domain.post.entity.Post;
import com.example.newsfeed.domain.post.repository.PostRepository;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시글 좋아요
    public void likePost(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        if (post.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 게시물에는 좋아요를 누를 수 없습니다.");
        }

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new RuntimeException("이미 좋아요를 누른 게시물입니다.");
        }

        likeRepository.save(new Like(user, post));
    }

    // 게시글 좋아요 취소
    public void cancelLikePost(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("좋아요 기록이 없습니다."));

        likeRepository.delete(like);
    }

    // 댓글 좋아요 (로직 동일)
    public void likeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 댓글에는 좋아요를 누를 수 없습니다.");
        }

        if (likeRepository.existsByUserAndComment(user, comment)) {
            throw new RuntimeException("이미 좋아요를 누른 댓글입니다.");
        }

        likeRepository.save(new Like(user, comment));
    }

    public void cancelLikeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        Like like = likeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new RuntimeException("좋아요 기록이 없습니다."));

        likeRepository.delete(like);
    }
}
