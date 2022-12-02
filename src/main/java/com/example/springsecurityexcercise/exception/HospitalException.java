package com.example.springsecurityexcercise.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HospitalException extends RuntimeException {

    ErrorCode errorCode;
    String message;

    @Override
    public String toString(){
        if(message == null){
            return errorCode.getErrorMessage();
        }
        return message;

    }
}
