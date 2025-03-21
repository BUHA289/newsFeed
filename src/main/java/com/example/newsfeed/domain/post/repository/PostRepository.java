package com.example.newsfeed.domain.post.repository;

import com.example.newsfeed.domain.post.entity.Post;
import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByUserInOrderByCreatedAtDesc(List<User> user, Pageable pageable);

    // 특정기간,유저 목록, 정렬
    Page<Post> findByUserInAndCreatedAtBetween(
            List<User> users,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
