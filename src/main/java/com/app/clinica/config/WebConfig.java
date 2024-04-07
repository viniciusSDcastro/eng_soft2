package com.app.clinica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.clinica.middlewares.AuthMiddleware;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private AuthMiddleware authMiddleware;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authMiddleware)
        .addPathPatterns("/**")
        .excludePathPatterns("/login");
  }
}
