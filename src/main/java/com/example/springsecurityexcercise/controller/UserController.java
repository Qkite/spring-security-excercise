package com.example.springsecurityexcercise.controller;


import com.example.springsecurityexcercise.domain.Response;
import com.example.springsecurityexcercise.domain.User;
import com.example.springsecurityexcercise.domain.dto.UserJoinRequest;
import com.example.springsecurityexcercise.domain.dto.UserJoinResponse;
import com.example.springsecurityexcercise.domain.dto.UserLoginRequest;
import com.example.springsecurityexcercise.domain.dto.UserLoginResponse;
import com.example.springsecurityexcercise.exception.ErrorCode;
import com.example.springsecurityexcercise.exception.HospitalException;
import com.example.springsecurityexcercise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        log.info(userJoinRequest.getName(), userJoinRequest.getEmail());

        User savedUser = userService.join(userJoinRequest);

        log.info(savedUser.getUserName(), savedUser.getEmailAddress());

        return Response.success(new UserJoinResponse(savedUser.getUserName(), savedUser.getEmailAddress()));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> join(@RequestBody UserLoginRequest userLoginRequest) {
        log.info(userLoginRequest.getName());

        String token = userService.login(userLoginRequest.getName(), userLoginRequest.getPassword());

        // 로그인 시 Encoded password does not look like BCrypt 에러가 뜨는 이유

        return Response.success(new UserLoginResponse(token));
    }








}
