package com.example.springbootfx.beanload.proxy;

public class ProxyStudentImpl implements ProxyStudentInterface {
    @Override
    public void learn() {
        System.out.println("autowiredStudent is learning!");
    }
}
