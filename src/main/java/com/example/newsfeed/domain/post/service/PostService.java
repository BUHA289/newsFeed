package com.example.newsfeed.domain.post.service;

import com.example.newsfeed.domain.comment.service.CommentService;
import com.example.newsfeed.domain.follwer.entity.Follower;
import com.example.newsfeed.domain.follwer.repository.FollowRepository;
import com.example.newsfeed.domain.post.dto.request.PostCreateRequestDto;
import com.example.newsfeed.domain.post.dto.request.PostDeleteRequestDto;
import com.example.newsfeed.domain.post.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.domain.post.dto.response.PostCreateResponseDto;
import com.example.newsfeed.domain.post.dto.response.PostResponseDto;
import com.example.newsfeed.domain.post.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.domain.post.entity.Post;
import com.example.newsfeed.domain.post.repository.PostRepository;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CommentService commentService;

    public PostCreateResponseDto createPost(Long id, PostCreateRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Post post = new Post(user.getId(), dto.getTitle(), dto.getContents());
        Post savedPost = postRepository.save(post);
        return new PostCreateResponseDto(savedPost.getId(), savedPost.getUser().getId(),
                savedPost.getTitle(), savedPost.getContents(), savedPost.getCreatedAt());
    }

    public Page<PostResponseDto> findAll(int page, int size) {
        // page를 0이하로 입력하면 첫번째 페이지 반환
        int adjustedPage = (page > 0) ? page - 1 : 0;
        // 생성일 기준으로 내림차순 정렬
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).map(posts -> new PostResponseDto(
                posts.getId(),
                posts.getUser().getId(),
                posts.getTitle(),
                posts.getContents(),
                posts.getCreatedAt()
        ));
    }

    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다.")
        );
        if (!post.getUser().getId().equals(dto.getUserId())) {
            throw new RuntimeException("게시물을 수정할 권한이 없습니다.");
        }
        post.update(dto.getTitle(), dto.getContents());
        return new PostUpdateResponseDto(post.getId(), post.getUser().getUsername(),
                post.getTitle(), post.getContents(), post.getCreatedAt()
                , post.getUpdatedAt());
    }

    public void deleteById(Long userId, PostDeleteRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다.")
        );
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("게시물을 삭제할 권한이 없습니다.");
        }
        // 게시물에 달린 댓글 먼저 삭제
        commentService.deleteAllByPost(post);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostByFollowing(Long userId, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다.")
        );
        List<Follower> followList = followRepository.findAllByFollower(findUser);

        List<User> followingUsers = followList.stream()
                .map(Follower::getFollowing)
                .collect(Collectors.toList());

        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findByUserInOrderByCreatedAtDesc(followingUsers, pageable);

        return postPage.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt()
        ));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostsByFollowingAndPeriod(
            Long userId,
            String startDateStr,
            String endDateStr,
            int page,
            int size
    ) {
        int adjustedPage = (page > 0) ? page - 1 : 0;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Follower> followList = followRepository.findAllByFollower(user);
        List<User> followingUsers = followList.stream()
                .map(Follower::getFollowing)
                .collect(Collectors.toList());

        // 날짜 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime startDate = LocalDate.parse(startDateStr, formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(endDateStr, formatter).atTime(23, 59, 59);

        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());

        Page<Post> postPage = postRepository.findByUserInAndCreatedAtBetween(
                followingUsers, startDate, endDate, pageable);

        return postPage.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt()
        ));
    }
}
