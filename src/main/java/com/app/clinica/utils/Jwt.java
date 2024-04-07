package com.app.clinica.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class Jwt {
  public static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos
  public static final String JWT_SECRET = "6f26713f-e58e-423a-ae35-909fe9d9229c";

  public static String generateJwt(String subject) {
    Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    String jwtToken = Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key)
        .compact();

    return jwtToken;
  }

  public static boolean validateJwt(String jwt) {
    try {
      Jwts.parserBuilder().setSigningKey(JWT_SECRET.getBytes()).build().parseClaimsJws(jwt);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
