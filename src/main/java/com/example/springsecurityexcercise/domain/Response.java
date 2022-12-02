package com.example.springsecurityexcercise.domain;

import com.example.springsecurityexcercise.domain.dto.UserJoinResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

    private String resultCode;
    private T result;


    public static <T> Response<T> success(T responseDto) {


        return new Response("SUCCESS", responseDto);
    }




}
