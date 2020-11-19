package com.example.springbootfx.autowired;

import com.example.springbootfx.autowired.entity.School;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlLoadTest {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beanXml.xml");
        School school = (School) context.getBean("school1");
        System.out.println(school.toString());
    }
}
