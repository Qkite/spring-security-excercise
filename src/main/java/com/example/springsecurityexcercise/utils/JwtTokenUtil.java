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
}
