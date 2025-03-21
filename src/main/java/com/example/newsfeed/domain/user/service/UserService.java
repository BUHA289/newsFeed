package com.example.newsfeed.domain.user.service;

import com.example.newsfeed.domain.config.PasswordEncoder;
import com.example.newsfeed.domain.user.dto.request.UserLoginRequestDto;
import com.example.newsfeed.domain.user.dto.request.UserSignUpRequestDto;
import com.example.newsfeed.domain.user.dto.request.UserUpdateRequsetDto;
import com.example.newsfeed.domain.user.dto.response.UserLoginResponseDto;
import com.example.newsfeed.domain.user.dto.response.UserResponseDto;
import com.example.newsfeed.domain.user.dto.response.UserSignUpResponseDto;
import com.example.newsfeed.domain.user.entity.User;
import com.example.newsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignUpResponseDto signUp (UserSignUpRequestDto dto) {
        String encode = passwordEncoder.encode(dto.getPassword());
        User user = new User(encode, dto.getEmail(), dto.getUsername(), dto.getAddress());
        userRepository.save(user);
        return new UserSignUpResponseDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    public UserLoginResponseDto login (UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return new UserLoginResponseDto(user.getId(),
                user.getEmail(), user.getUsername());
    }

    public UserResponseDto findById (Long id, Long targetid) {

        User userTarget = userRepository.findById(targetid).orElseThrow(
                () -> new RuntimeException("id를 찾을수 없습니다.")
        );
        String address = id.equals(targetid) ? userTarget.getAddress() : null;

        return new UserResponseDto(userTarget.getId(),
                userTarget.getUsername(),
                userTarget.getEmail(),
                address,
                userTarget.getCreatedAt());
    }

    public void updatePassword(Long id, UserUpdateRequsetDto dto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("사용자를 찾을수 없습니다."));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("기존의 비밀번호가 일치하지 않습니다.");
        }

        // 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("새 비밀번호를 기존의 비밀번호와 동일하게 변경할 수 없습니다.");    }

        user.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

}
