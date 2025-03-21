package com.example.newsfeed.domain.user.controller;

import com.example.newsfeed.domain.user.dto.request.UserLoginRequestDto;
import com.example.newsfeed.domain.user.dto.request.UserSignUpRequestDto;
import com.example.newsfeed.domain.user.dto.request.UserUpdateRequsetDto;
import com.example.newsfeed.domain.user.dto.response.UserLoginResponseDto;
import com.example.newsfeed.domain.user.dto.response.UserResponseDto;
import com.example.newsfeed.domain.user.dto.response.UserSignUpResponseDto;
import com.example.newsfeed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserSignUpResponseDto> signUp (@RequestBody UserSignUpRequestDto dto) {
       return ResponseEntity.ok(userService.signUp(dto));
    }
    // 객체 생성인가 스태틱인가
    // 유저 로그인
    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDto> login (@RequestBody UserLoginRequestDto dto) {
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }

    // 유저 단건 조회
    @GetMapping("users/{targetid}")
    public ResponseEntity<UserResponseDto> findById (@PathVariable Long id,
            @PathVariable Long targetid) {
        return ResponseEntity.ok(userService.findById(id, targetid));
    }

    // 유저 수정
    @PutMapping("users/{id}")
    public ResponseEntity<Void> updatePassword (@PathVariable Long id,
                                                @RequestBody UserUpdateRequsetDto dto) {
        userService.updatePassword(id, dto); //
        return ResponseEntity.ok().build();
    }
}
