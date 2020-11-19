package com.example.springbootfx.beanload.annotation;

import org.springframework.stereotype.Component;

@Component
public class AnnotationStudent {
    public AnnotationStudent(){
        System.out.println("annotationStudent construct!");
    }
    void learn(){
        System.out.println("annotationStudent is learning!");
    }
}
