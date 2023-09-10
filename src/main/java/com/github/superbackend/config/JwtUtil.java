package com.github.superbackend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    private final String secret = "yourSecretKey"; // 시크릿 키 (보안상 안전한 곳에 저장해야 함)
    private final int expirationMs = 86400000; // 토큰 유효 기간 (24시간)

    public String generateToken(long memberId, String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMs);
        return Jwts.builder()
                .claim("memberId", memberId) // memberId 클레임 추가
                .claim("email", email)       // email 클레임 추가
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
