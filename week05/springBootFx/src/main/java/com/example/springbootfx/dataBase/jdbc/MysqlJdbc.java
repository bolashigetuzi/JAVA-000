package com.example.springbootfx.dataBase.jdbc;

import java.sql.*;
import java.util.*;

public class MysqlJdbc {
    private static final String url = "jdbc:mysql://localhost:3306/testDataBase?serverTimezone=GMT%2B8";
    private static final String userName = "kinggrid";
    private static final String pwd = "kinggrid";

    private static final String driverStr = "com.mysql.cj.jdbc.Driver";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    public void getConnection(){
        try{
            Class.forName(driverStr);
            connection = DriverManager.getConnection(url,userName,pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void close() throws SQLException {
        preparedStatement.close();
        connection.close();
        System.out.println("Connection close");
    }

    public List<Map<String,Object>> queryList(String sql){
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String empName = resultSet.getString(2);
                String year = resultSet.getString(3);
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("empName",empName);
                map.put("year",year);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

     public void insertEmployee(String sql){
         try {
             preparedStatement = connection.prepareStatement(sql);
             preparedStatement.execute();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public static void main(String[] args) {
        String insertSql = "insert into t_employee values (1,'张三','35')";
        MysqlJdbc mysqlJdbc = new MysqlJdbc();
        mysqlJdbc.getConnection();
        mysqlJdbc.insertEmployee(insertSql);
        String querySql = "select * from t_employee";
        List<Map<String,Object>> list = mysqlJdbc.queryList(querySql);
        list.forEach(l->System.out.println(l.toString()));
     }
}
