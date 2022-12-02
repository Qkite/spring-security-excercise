package com.example.springsecurityexcercise.service;

import com.example.springsecurityexcercise.domain.User;
import com.example.springsecurityexcercise.domain.dto.UserJoinRequest;
import com.example.springsecurityexcercise.exception.ErrorCode;
import com.example.springsecurityexcercise.exception.HospitalException;
import com.example.springsecurityexcercise.repository.UserRepository;
import com.example.springsecurityexcercise.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000*60*60L;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User join(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getName())
                .ifPresent(user -> {throw new HospitalException(ErrorCode.DUPLICATED_USER_NAME, "사용자 " + userJoinRequest.getName() + "이 존재합니다.");
                });


        User user = User.builder()
                .userName(userJoinRequest.getName())
                .emailAddress(userJoinRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(userJoinRequest.getPassword()))
                .build();

        userRepository.save(user);

        return user;

    }

    public String login(String userName, String password) {

        User selectedUser = userRepository.findByUserName(userName).orElseThrow(() -> new HospitalException(ErrorCode.NOT_FOUND_USER_NAME, "아이디를 찾을 수 없습니다."));
        

        if(!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())){
            
            // matches(encoding하지 않은 pw, encoding한 pw) 자리 잘 맞추기
            throw new HospitalException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘 못 입력하셨습니다.");
        }

        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs);

        return token;



    }
}
