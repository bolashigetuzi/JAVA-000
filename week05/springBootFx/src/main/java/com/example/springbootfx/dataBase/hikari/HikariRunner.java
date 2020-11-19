package com.example.springbootfx.dataBase.hikari;

import com.example.springbootfx.dataBase.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HikariRunner implements CommandLineRunner {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from t_employee";
        List<Employee> employeeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Employee.class));
        employeeList.stream().forEach(System.out::println);
    }
}
