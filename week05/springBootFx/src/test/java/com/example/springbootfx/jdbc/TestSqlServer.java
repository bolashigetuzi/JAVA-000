package com.example.springbootfx.jdbc;

import com.example.springbootfx.dataBase.entity.*;
import com.example.springbootfx.dataBase.jdbc.SqlServerJdbc;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class TestSqlServer {
    @Test
    public void test(){
        TestSqlServer testSqlServer = new TestSqlServer();
        testSqlServer.executeUpdate();
    }
    @Test
    public void testPre() throws SQLException {
        TestSqlServer testSqlServer = new TestSqlServer();
        testSqlServer.prepareData();
    }

    public void prepareData() throws SQLException {
        SqlServerJdbc sqlServerJdbc = new SqlServerJdbc();
        sqlServerJdbc.getConnection();
        long tempBefore = System.currentTimeMillis();
        List<TargetBaseTemp> targetBaseTempList = sqlServerJdbc.targetBaseTempList();
        long tempAfter = System.currentTimeMillis();
        System.out.println("temp执行时间：" + (tempAfter-tempBefore) + "毫秒！");
        List<ProductDirectoryMap> productDirectoryMapList = sqlServerJdbc.productDirectoryMapList();
        long mapAfter = System.currentTimeMillis();
        System.out.println("map执行时间：" + (mapAfter-tempAfter) + "毫秒！");
        List<TargetData> targetDataList = sqlServerJdbc.targetDataList();
        long dataAfter = System.currentTimeMillis();
        System.out.println("data执行时间：" + (dataAfter-mapAfter) + "毫秒！");
        targetDataList = sqlServerJdbc.matchDataDirectory(targetBaseTempList, targetDataList, productDirectoryMapList);
        long matchAfter = System.currentTimeMillis();
        System.out.println("匹配执行时间：" + (matchAfter-dataAfter) + "毫秒！");
        sqlServerJdbc.updateDirectoryName(targetDataList);
        long updateAfter = System.currentTimeMillis();
        System.out.println("更新执行时间：" + (updateAfter-matchAfter) + "毫秒！");
        sqlServerJdbc.close();
    }

    public void executeUpdate(){
        SqlServerJdbc sqlServerJdbc = new SqlServerJdbc();
        sqlServerJdbc.getConnection();
        long dataBefore = System.currentTimeMillis();
        List<TargetData> targetDataList = sqlServerJdbc.targetDataList();
        long dataAfter = System.currentTimeMillis();
        System.out.println("temp执行时间：" + (dataAfter-dataBefore) + "毫秒！");
        List<DeliverExtraData> extraDataList = sqlServerJdbc.deliverExtraDataList();
        long extraAfter = System.currentTimeMillis();
        System.out.println("extra执行时间：" + (extraAfter-dataAfter) + "毫秒！");
        List<DeliveryData> deliveryDataList = sqlServerJdbc.deliveryDataList();
        long deliveryAfter = System.currentTimeMillis();
        System.out.println("extra执行时间：" + (deliveryAfter-extraAfter) + "毫秒！");
        targetDataList = sqlServerJdbc.matchDataDescPre(targetDataList,extraDataList);
        deliveryDataList = sqlServerJdbc.matchDataDesc(deliveryDataList,targetDataList);
        long matchAfter = System.currentTimeMillis();
        System.out.println("匹配执行时间：" + (matchAfter-extraAfter) + "毫秒！");
//        int successNum = sqlServerJdbc.updateDeliverInfo(deliveryDataList);
//        long updateAfter = System.currentTimeMillis();
//        System.out.println("temp执行时间：" + (updateAfter-dataAfter) + "毫秒！共执行" + successNum + "条！");
    }
}
