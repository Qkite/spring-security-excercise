package com.example.springsecurityexcercise.controller;

import com.example.springsecurityexcercise.service.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class UserControllerTest {

    @MockBean
    UserService userService;

}