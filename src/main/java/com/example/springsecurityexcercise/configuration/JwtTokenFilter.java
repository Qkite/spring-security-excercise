package com.example.springsecurityexcercise.configuration;

import com.example.springsecurityexcercise.service.UserService;
import com.example.springsecurityexcercise.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// OncePerRequestFilter: 요청할 떄 마다 token을 보여주는 방식

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request할 때 header에서 token을 가져옴
        // Authorization header: Bearer ~~~
        // Bearer 토큰: 오어스 인증방식 이용함

        String token;

        try{
            token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];

            // 토큰을 까기 위해서 secretKey가 필요함
            JwtTokenUtil.isExpired(token, secretKey);

        } catch (Exception e){
            log.error("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;

        }

        if(JwtTokenUtil.isExpired(token, secretKey)){
            filterChain.doFilter(request, response);
            return;
        }

        // 문열어주기
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("", null, List.of(new SimpleGrantedAuthority( "USER")));
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // 권한 부여
        filterChain.doFilter(request, response);



    }
}
