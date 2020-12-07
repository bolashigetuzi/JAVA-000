package com.datasource.demo;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 137531毫秒
 */
public class TestBatchInsert {
    @Test
    public void insertBatchEmp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/test_slave", "root", "root");
        try{
            conn.setAutoCommit(false);

            String sql = "INSERT INTO `t_emp`(empName) VALUES(?);";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            Long before = System.currentTimeMillis();
            int i=1;
            while(i<1000000){
                ptmt.setString(1, "员工"+i);
                ptmt.addBatch();
                i++;
            }
            ptmt.executeBatch();
            conn.commit();
            Long after = System.currentTimeMillis();
            System.out.println(after-before);
        }catch (Exception e){
            e.printStackTrace();
            conn.rollback();
        }
    }
}
