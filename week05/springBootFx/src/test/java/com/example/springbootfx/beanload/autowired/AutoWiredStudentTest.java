package com.example.springbootfx.beanload.autowired;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AutoWiredStudent.class)
public class AutoWiredStudentTest {
    @Autowired
    private AutoWiredStudent autoWiredStudent;
    @Test
    public void test(){
        autoWiredStudent.learn();
    }
}
