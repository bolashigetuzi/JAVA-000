package com.datasource.demo.service;

import com.datasource.demo.entity.Emp;
import com.datasource.demo.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private EmpMapper empMapper;

    /**
     * 获取已更新的字段
     * @param empNo
     * @param empName
     * @return
     */
    public Emp getInsertName(int empNo, String empName){
        Emp emp = new Emp();
        emp.setEmpNo(empNo);
        emp.setEmpName(empName);
        empMapper.update(emp);
        return empMapper.getOne(empNo);
    }
}
