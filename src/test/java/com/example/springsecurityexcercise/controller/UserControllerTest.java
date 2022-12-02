package com.example.springsecurityexcercise.controller;

import com.example.springsecurityexcercise.domain.User;
import com.example.springsecurityexcercise.domain.dto.UserDto;
import com.example.springsecurityexcercise.domain.dto.UserJoinRequest;
import com.example.springsecurityexcercise.domain.dto.UserJoinResponse;
import com.example.springsecurityexcercise.domain.dto.UserLoginRequest;
import com.example.springsecurityexcercise.exception.ErrorCode;
import com.example.springsecurityexcercise.exception.HospitalException;
import com.example.springsecurityexcercise.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser // 없으면 401 Unauthorized 에러가 발생
    void join_success() throws Exception {

        String userName = "aabbcc";
        String email = "aaa@gmail.com";
        String password = "1k2jds";

        when(userService.join(any()))
                .thenReturn(mock(User.class));
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password, email))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser // 없으면 401 Unauthorized 에러가 발생
    void join_fail() throws Exception {

        String userName = "aabbcc";
        String password = "1k2jds";
        String email = "aaa@gmail.com";

        when(userService.join(any()))
                .thenThrow(new HospitalException(ErrorCode.DUPLICATED_USER_NAME, "아이디가 중복됩니다."));
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password, email))))
                .andDo(print())
                .andExpect(status().isConflict());
    }


    // Test에 다 401이 뜨는 이유...?
    @Test
    @DisplayName("로그인 성공")
    @WithMockUser // 없으면 401 Unauthorized 에러가 발생
    void login_success() throws Exception {

        String userName = "aabbcc";
        String password = "1k2jds";

        when(userService.login(any(), any()))
                .thenReturn("token");
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패")
    @WithMockUser // 없으면 401 Unauthorized 에러가 발생
    void login_fail1() throws Exception {

        String userName = "aabbcc";
        String password = "1k2jds";

        when(userService.login(any(), any()))
                .thenThrow(new HospitalException(ErrorCode.NOT_FOUND_USER_NAME, "사용자 이름을 찾지 못했습니다."));
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패")
    @WithMockUser // 없으면 401 Unauthorized 에러가 발생
    void login_fail2() throws Exception {

        String userName = "aabbcc";
        String password = "1k2jds";

        when(userService.login(any(), any()))
                .thenThrow(new HospitalException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호 입니다."));
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }



}