package com.example.springsecurityexcercise.controller;


import com.example.springsecurityexcercise.domain.dto.ReviewCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewController {

    @PostMapping
    public String write(@RequestBody ReviewCreateRequest request, Authentication authentication){
        log.info("Controller user: {}", authentication.getName());


        return "리뷰 등록에 성공했습니다.";
    }
}
