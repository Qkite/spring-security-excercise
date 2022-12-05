package com.example.springsecurityexcercise.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalTime;
import java.util.Date;

public class JwtTokenUtil {

    public static String createToken(String userName, String key, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("userName", userName);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }

    public static Claims extractClaims(String token, String key){

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        // Secret key를 이용해서 token을 parser
    }

    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        return expiredDate.before(new Date());
        // 토큰에 있는 유효기간이 지금보다 전인지(만료되었는지) 확인
    }
}
