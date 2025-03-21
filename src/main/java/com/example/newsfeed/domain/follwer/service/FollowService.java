package com.example.newsfeed.domain.follwer.service;

import com.example.newsfeed.domain.follwer.entity.Follower;
import com.example.newsfeed.domain.follwer.repository.FollowRepository;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void addFollowing(Long userId, Long followingUserId) {
        User findUser = findUserByIdOrElseThrow(userId);
        User followingUser = findUserByIdOrElseThrow(followingUserId);

        // 팔로워 테이블에 팔로잉(내가 추가한 유저 한명) 생성.
        Follower follower = new Follower(findUser, followingUser);
        followRepository.save(follower);
    }

    // Service 레벨에서 NULL 체크 (유저 ID)
    public User findUserByIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 아이디와 일치하는 유저가 없습니다. id = " + userId));
    }
}