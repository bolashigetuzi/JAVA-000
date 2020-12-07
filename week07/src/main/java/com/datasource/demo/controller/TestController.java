package com.datasource.demo.controller;

import com.datasource.demo.entity.Emp;
import com.datasource.demo.entity.ResData;
import com.datasource.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("getEmpName")
    public void validateMasterSyncSlave(
            @RequestParam("empNo")int empNo,
            @RequestParam("empName")String empName,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(200);
        response.setCharacterEncoding("gbk");
//        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        ResData resData = new ResData();
        Emp emp = testService.getInsertName(empNo,empName);
        resData.setMessage(emp.getEmpName());
        resData.setSuccess(true);
        String testStr = "飞机";
        response.getWriter().println(new String(testStr.getBytes("UTF-8")));
    }
}
