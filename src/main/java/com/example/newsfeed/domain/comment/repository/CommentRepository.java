package com.example.newsfeed.domain.comment.repository;

import com.example.newsfeed.domain.comment.entity.Comment;
import com.example.newsfeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    void deleteAllByPost(Post post); // 게시글 삭제 시 댓글 삭제용
}
