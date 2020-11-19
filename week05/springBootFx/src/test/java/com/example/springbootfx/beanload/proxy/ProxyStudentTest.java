package com.example.springbootfx.beanload.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyStudentTest {
    @Test
    public void test(){
        ProxyStudentImpl proxyStudent =new ProxyStudentImpl();
        ClassLoader classLoader = proxyStudent.getClass().getClassLoader();
        Class[] stuInterfaces = proxyStudent.getClass().getInterfaces();
        InvocationHandler handler = new StudentProxyHandler(proxyStudent);

        ProxyStudentInterface proxyStudentInterface = (ProxyStudentInterface) Proxy.newProxyInstance(classLoader,stuInterfaces,handler);
        proxyStudentInterface.learn();
    }
}
