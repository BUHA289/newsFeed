package com.example.newsfeed.domain.follwer.repository;

import com.example.newsfeed.domain.follwer.entity.Follower;
import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByFollower(User follower);
}
