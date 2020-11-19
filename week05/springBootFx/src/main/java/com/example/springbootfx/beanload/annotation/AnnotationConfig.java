package com.example.springbootfx.beanload.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfig {
    @Bean
    public AnnotationStudent getAnnotationStudent(){
        return new AnnotationStudent();
    }
}
