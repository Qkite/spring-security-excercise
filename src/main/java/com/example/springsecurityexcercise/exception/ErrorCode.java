package com.example.springsecurityexcercise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName이 중복됩니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER_NAME(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다.")
    
    ;
    
    // enum에 들어가는 필드의 속성 -> AllArgsConstructor를 해주어야 받을 수 있음
    private HttpStatus httpStatus;
    private String errorMessage;

    ErrorCode(String errorMessage){

        this.errorMessage = errorMessage;
    }


}
