package com.example.springsecurityexcercise.domain.dto;

import com.example.springsecurityexcercise.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.codec.StringDecoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {

    private String name;
    private String password;
    private String email;


    public User toEntity(){
        return User.builder()
                .userName(name)
                .emailAddress(email)
                .password(password)
                .build();


    }




}
