package com.app.clinica.validators;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.app.clinica.utils.Jwt;

import java.util.Map;

@Aspect
@Component
public class TokenValidationAspect {

  @Around("@annotation(TokenValidator)")
  public Object tokenValidator(ProceedingJoinPoint joinPoint) throws Throwable {
    Map<String, String> headers = headerExtractFromJointPoint(joinPoint);
    if (headers == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    String token = headers.get("token");

    if (token == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    String[] tokenArray = token.split(" ");

    boolean isValid = Jwt.validateJwt(tokenArray[1]);

    if (isValid) {
      return joinPoint.proceed();
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, String> headerExtractFromJointPoint(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();

    for (Object arg : args) {
      if (arg instanceof Map<?, ?> headers) {
        return (Map<String, String>) headers;
      }
    }
    return null;
  }
}
