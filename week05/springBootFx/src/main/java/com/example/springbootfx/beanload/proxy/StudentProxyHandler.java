package com.example.springbootfx.beanload.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StudentProxyHandler implements InvocationHandler {
    private Object object;

    public StudentProxyHandler(Object object){
        this.object = object;
    }
    private void preHandler(){
        System.out.println("before invoke handler");
    }

    private void afterwardsHandler(){
        System.out.println("after invoke handler");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preHandler();
        method.invoke(object,args);
        afterwardsHandler();
        return null;
    }


}
