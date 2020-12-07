package com.datasource.demo;

import com.datasource.demo.entity.Emp;
import com.datasource.demo.mapper.EmpMapper;
import com.datasource.demo.util.HttpUtil;
import groovy.util.logging.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DemoApplicationTests {
    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testRest(){
        String url = "http://127.0.0.1:8080/test/getEmpName";
        String param = "empNo=1&empName=qianqi";
        String resPos = HttpUtil.sendGet(url,param);
        System.out.println(resPos);
    }

    @Test
    void test() {
        Emp emp = new Emp();
        emp.setEmpName("张三");
        empMapper.insert(emp);
        //先手动插入一条
        Emp empSelect = empMapper.getOne(2);
        Assert.assertEquals("王五",empSelect.getEmpName());
    }
}
