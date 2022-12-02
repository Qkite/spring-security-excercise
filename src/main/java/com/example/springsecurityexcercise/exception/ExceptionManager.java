package com.example.springsecurityexcercise.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        // RuntimeException이 나면 Controller 대신 이곳에서 리턴을 해줌

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());

        // INTERNAL_SERVER_ERROR를 리턴하고
        // ResponseBody에 e.getMassage()를 추가(에러 메시지를)해서 보냄

    }

    @ExceptionHandler(HospitalException.class)
    public ResponseEntity<?> hopitalExceptionHandler(HospitalException e){

        return ResponseEntity.status(e.errorCode.getHttpStatus())
                .body(e.message);
    }



}
