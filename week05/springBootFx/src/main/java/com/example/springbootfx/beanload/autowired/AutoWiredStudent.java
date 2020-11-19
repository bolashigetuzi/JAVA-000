package com.example.springbootfx.beanload.autowired;

import org.springframework.stereotype.Component;

@Component
public class AutoWiredStudent {
    public AutoWiredStudent() {
        System.out.println("autowiredStudent construct!");
    }
    public void learn(){
        System.out.println("autowiredStudent is learning!");
    }
}
