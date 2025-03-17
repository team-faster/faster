package com.faster.user.app.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final Key accessKey;
  private final Key refreshKey;
  private final SecretKey secretKey;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpiration;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpiration;

  public JwtProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.accessKey = Keys.hmacShaKeyFor(keyBytes);
    this.refreshKey = Keys.hmacShaKeyFor(keyBytes);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(String id) {
    return Jwts.builder()
        .claim("userId", id)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+ accessTokenExpiration))
        .signWith(secretKey)
        .compact();
  }

  public String createRefreshToken(String id) {
    return Jwts.builder()
        .claim("userId", id)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+ refreshTokenExpiration))
        .signWith(secretKey)
        .compact();
  }

  // TODO: try-catch 를 없애고 더 이쁘게 풀어낼 수 없는지 고민중 ...
  public Boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  public String getUserIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.get("userId", String.class);
  }

}