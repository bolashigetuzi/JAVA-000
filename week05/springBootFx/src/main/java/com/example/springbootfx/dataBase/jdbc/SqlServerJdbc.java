package com.example.springbootfx.dataBase.jdbc;

import com.example.springbootfx.dataBase.entity.*;
import org.springframework.beans.BeanUtils;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SqlServerJdbc {
    private static final String url = "jdbc:sqlserver://192.168.0.136:1433;databasename=";
    private static final String dataBaseName = "NEWCRM2";
    private static final String userName = "sa";
    private static final String pwd = "123456";
    private static final String driverStr = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public List<DeliveryData> deliveryDataList(){
        List<DeliveryData> deliveryDataList = new ArrayList<>();
        String sql ="SELECT d.id,d.create_date createDate FROM delivery_info d";
        try{
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DeliveryData deliveryData = new DeliveryData();
                String id =  resultSet.getString(1);;
                java.util.Date createDate = resultSet.getDate(2);
                deliveryData.setId(id);
                deliveryData.setCreateDate(createDate);
                deliveryDataList.add(deliveryData);
            }
            System.out.println("查询delivery_info成功：共" + deliveryDataList.size() + "条！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return deliveryDataList;
    }

    public List<TargetData> matchDataDescPre(List<TargetData> targetDataList, List<DeliverExtraData> deliverExtraDataList){
        List<TargetData> dataAddList = new ArrayList<>();
        Integer executeSuccess = 0;
        Integer notSuccess = 0;
        for(TargetData data:targetDataList){
            String clientName = data.getClientName();
            String authorityName = data.getAuthorityName();
            String directoryName = data.getDirectoryName();
            String contractNo = data.getContractNo();
            String moduleStr = data.getModuleStr();
            boolean isMatched = false;
            boolean isNotFirstMatch = false;
            String errorType = "客户和产品均不匹配";
            for(DeliverExtraData extraData:deliverExtraDataList){
                String clientNameExtra = extraData.getClientName();
                String authorityClient = extraData.getAuthorityClient();
                String packageName = extraData.getPackageName();
                String receiveDate = extraData.getReceiveDate();
                String signDigest = extraData.getSignDigest();
                String authorityCode = extraData.getAuthorityCode();
                if(isEmpty(directoryName)){
                    errorType = "未匹配到目录";
                    continue;
                }
                boolean strMatch = clientName.equals(clientNameExtra) && authorityClient.equals(authorityName)
                        && packageName.startsWith(directoryName);

                if(strMatch){
                    executeSuccess++;
                    String signCert = "";
                    if(!isEmpty(signDigest)){
                        signCert = signCert + signDigest;
                        if(!isEmpty(authorityCode)){
                            signCert = signCert + "@" + authorityCode;
                        }
                    }
                    if(isNotFirstMatch){
                        TargetData t = new TargetData();
                        BeanUtils.copyProperties(data, t);
                        t.setPackageName(packageName);
                        t.setSignDate(receiveDate);
                        t.setSignCert(signCert);
                        dataAddList.add(t);
                    }else{
                        data.setSignDate(receiveDate);
                        data.setPackageName(packageName);
                        data.setSignCert(signCert);
                        isMatched = true;
                        isNotFirstMatch = true;
                    }
                }else{
                    if(!clientName.equals(clientNameExtra) && authorityClient.equals(authorityName)
                            && packageName.startsWith(directoryName)){
                        errorType = "采购客户不匹配";
                    }else if(clientName.equals(clientNameExtra) && !authorityClient.equals(authorityName)
                            && packageName.startsWith(directoryName)){
                        errorType = "授权客户不匹配";
                    }else if(clientName.equals(clientNameExtra) && authorityClient.equals(authorityName)
                            && !packageName.startsWith(directoryName)){
                        errorType = "目录名称不匹配：" + moduleStr;
                    }
                }
            }
            if(!isMatched){
                notSuccess++;
                String logTxt = errorType + ">------" + clientName + ":" + authorityName + ":" + directoryName + ":" + contractNo;
                System.out.println(logTxt);
            }
        }
        System.out.println("共匹配成功：" + executeSuccess + "条！");
        System.out.println("未匹配成功：" + notSuccess + "条！");
        targetDataList.addAll(dataAddList);
        return targetDataList;
    }

    public List<DeliveryData> matchDataDesc(List<DeliveryData> deliveryDataList,
                                          List<TargetData> targetDataList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        Integer executeSuccess = 0;
        Integer notSuccess = 0;
        for(DeliveryData info:deliveryDataList){
            String id = info.getId();
            java.util.Date createDate = info.getCreateDate();
            String contractNo = "";
            boolean isMatched = false;
            StringBuffer sb = new StringBuffer();
            String dateStr = sdf.format(createDate);
            sb.append(">-----发货日期：" + dateStr + "}}不匹配的签收日期有：");
            for(TargetData data:targetDataList){
                String idTar = data.getId();
                String signDate = data.getSignDate();
                if(id.equals(idTar)){
                    contractNo = data.getContractNo();
                    if(!isEmpty(signDate) && signDate.equals(dateStr)){
                        isMatched = true;
                        executeSuccess ++;
                        info.setPackageName(data.getPackageName());
                        info.setSignDate(signDate);
                        info.setSignCert(data.getSignCert());
                        break;
                    }else{
                        sb.append(signDate + ",");
                    }
                }
            }
            if(!isMatched){
                notSuccess++;
                sb.append(contractNo);
                System.out.println(sb.toString());
            }
        }
        System.out.println("共匹配日期成功：" + executeSuccess + "条！");
        System.out.println("未匹配日期成功：" + notSuccess + "条！");
        return deliveryDataList;
    }

    public Map<String,String> getModuleNames(){
        Map<String,String> map = new HashMap<>();
        String sql = "";
        try{
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id =  resultSet.getString(1);;
                String moduleName =  resultSet.getString(2);
                if(map.containsKey(id)){
                    String moduleNameStr = map.get(id);
                    map.put(id, moduleNameStr + moduleName + ",");
                }else{
                    map.put(id, moduleName + ",");
                }
            }
            System.out.println("查询delivery_info成功：共" + map.size() + "条！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<TargetData> matchDataDirectory(List<TargetBaseTemp> targetBaseTempList, List<TargetData> targetDataList,
                                               List<ProductDirectoryMap> productDirectoryMapList){
        Integer executeManyNum = 0;
        for(TargetData data:targetDataList){
            String idData = data.getId();
            Integer moduleNum = data.getModuleNum();
            if(0 == moduleNum){
                continue;
            }
            if(moduleNum > 1){
                executeManyNum++;
            }
            Integer matchIndex = 0;
            StringBuffer moduleStrBuffer = new StringBuffer();
            loop:for(TargetBaseTemp temp:targetBaseTempList){
                String idTemp = temp.getId();
                String productNameTemp = temp.getProductName();
                String moduleName = temp.getModuleName();
                if(idData.equals(idTemp)){
                    matchIndex++;
                    for(ProductDirectoryMap productDirectoryMap:productDirectoryMapList){
                        String productNameMap = productDirectoryMap.getProductName();
                        String moduleNameMap = productDirectoryMap.getModuleName();
                        String directoryName = productDirectoryMap.getDirectoryName();
                        if(productNameTemp.equals(productNameMap) && moduleNameMap.contains(moduleName)){
                            data.setDirectoryName(directoryName);
                            moduleStrBuffer.append(moduleName + ",");
                            if(matchIndex < moduleNum){
                                break;
                            }else{
                                data.setModuleStr(moduleStrBuffer.toString().substring(0, moduleStrBuffer.toString().length()-1));
                                break loop;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("一共有多个功能模块的记录为：" + executeManyNum + "条！");
        return targetDataList;
    }

    public Integer updateDeliverInfo(List<DeliveryData> deliveryData){
        String sql = "update delivery_info set sign_date=?, software_package_name=?, sign_cert=? where id=?";
        Integer successIndex = 0;
        try{
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for(DeliveryData data:deliveryData){
                preparedStatement.setString(1, data.getSignDate());
                preparedStatement.setString(2, data.getPackageName());
                preparedStatement.setString(3, data.getSignCert());
                preparedStatement.setString(4, data.getId());
                preparedStatement.addBatch();
            }
            int[] successNum = preparedStatement.executeBatch();
            successIndex = Arrays.asList(successNum).stream().filter(i -> i.equals(1)).collect(Collectors.toList()).size();
            System.out.println("updateAllDataSuccessNum:" + successIndex);
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            try {
                // 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
                if(!connection.isClosed()){
                    connection.rollback();//4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
                    System.out.println("插入失败，回滚！");
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return successIndex;
    }

    /**
     * update table target_data
     * @param targetDataList entityList
     * @return update number
     */
    public Integer updateDirectoryName(List<TargetData> targetDataList){
        String sql_update = "update target_data set directoryName=?,moduleStr=?  where id=? ";
        Integer successIndex;
        try{
            preparedStatement = connection.prepareStatement(sql_update);
            connection.setAutoCommit(false);
            for(TargetData data:targetDataList){
                preparedStatement.setString(1, data.getDirectoryName());
                preparedStatement.setString(2, data.getModuleStr());
                preparedStatement.setString(3, data.getId());
                preparedStatement.addBatch();
            }
            int[] successNum = preparedStatement.executeBatch();
            successIndex = Arrays.asList(successNum).stream().filter(i -> i.equals(1)).collect(Collectors.toList()).size();
            System.out.println("updateDirectoryNameSuccessNum:" + successIndex);
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            e.printStackTrace();
            try {
                // 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
                if(!connection.isClosed()){
                    connection.rollback();//4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
                    System.out.println("插入失败，回滚！");
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }
    public List<DeliverExtraData> deliverExtraDataList(){
        List<DeliverExtraData> deliverExtraDataList = new ArrayList<>();
        String sql = "select * from deliver_extra_data";
        try{
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DeliverExtraData deliverExtraData = new DeliverExtraData();
                String clientName =  resultSet.getString(1);;
                String authorityClient =  resultSet.getString(2);
                String packageName =  resultSet.getString(3);
                String receiveDate =  resultSet.getString(4);
                String signDate =  resultSet.getString(5);
                String signDigest =  resultSet.getString(6);
                String authorityCode =  resultSet.getString(7);
                deliverExtraData.setClientName(clientName);
                deliverExtraData.setAuthorityClient(authorityClient);
                deliverExtraData.setPackageName(packageName);
                deliverExtraData.setReceiveDate(receiveDate);
                deliverExtraData.setSignDate(signDate);
                deliverExtraData.setSignDigest(signDigest);
                deliverExtraData.setAuthorityCode(authorityCode);
                deliverExtraDataList.add(deliverExtraData);
            }
            System.out.println("查询deliver_extra_data成功：共" + deliverExtraDataList.size() + "条！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return deliverExtraDataList;
    }

    /**
     * select ProductDirectoryMap
     * @return dataList
     */
    public List<ProductDirectoryMap> productDirectoryMapList(){
        String sql = "select * from product_directory_map";
        List<ProductDirectoryMap> productDirectoryMapList = new ArrayList<>();
        try{
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productName = resultSet.getString(1);
                String moduleName = resultSet.getString(2);
                String directoryName = resultSet.getString(3);
                ProductDirectoryMap productDirectoryMap = new ProductDirectoryMap();
                productDirectoryMap.setProductName(productName);
                productDirectoryMap.setModuleName(moduleName);
                productDirectoryMap.setDirectoryName(directoryName);
                productDirectoryMapList.add(productDirectoryMap);
            }
            System.out.println("查询ProductDirectoryMap成功：共" + productDirectoryMapList.size() + "条！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return productDirectoryMapList;
    }

    /**
     * target data before match
     * @return dataList
     */
    public List<TargetData> targetDataList(){
        String sql_data = "select * from target_data";
        List<TargetData> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql_data);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String productInfoId = resultSet.getString(2);
                String contractId = resultSet.getString(3);
                Date createDate = resultSet.getDate(4);
                String clientName = resultSet.getString(5);
                String productName = resultSet.getString(7);
                String authorityName = resultSet.getString(6);
                Integer moduleNum = resultSet.getInt(8);
                String directoryName = resultSet.getString(9);
                String moduleStr = resultSet.getString(10);
                String contractNo = resultSet.getString(14);
                TargetData targetData = new TargetData();
                targetData.setId(id);
                targetData.setProductInfoId(productInfoId);
                targetData.setContractId(contractId);
                targetData.setCreateDate(createDate);
                targetData.setAuthorityName(authorityName);
                targetData.setClientName(clientName);
                targetData.setProductName(productName);
                targetData.setModuleNum(moduleNum);
                targetData.setModuleStr(moduleStr);
                targetData.setDirectoryName(directoryName);
                targetData.setContractNo(contractNo);
                list.add(targetData);
            }
            System.out.println("查询temp成功：共" + list.size() + "条！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * get base module data
     * @return dataList
     */
    public List<TargetBaseTemp> targetBaseTempList(){
        String sql_temp = "select * from target_base_temp";
        List<TargetBaseTemp> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql_temp);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String productInfoId = resultSet.getString(2);
                String contractId = resultSet.getString(3);
                Date createDate = resultSet.getDate(4);
                String clientName = resultSet.getString(5);
                String authorityName = resultSet.getString(6);
                String productName = resultSet.getString(7);
                String moduleName = resultSet.getString(8);
                String contractNo = resultSet.getString(9);
                TargetBaseTemp targetBaseTemp = new TargetBaseTemp();
                targetBaseTemp.setId(id);
                targetBaseTemp.setProductInfoId(productInfoId);
                targetBaseTemp.setContractId(contractId);
                targetBaseTemp.setCreateDate(createDate);
                targetBaseTemp.setClientName(clientName);
                targetBaseTemp.setAuthorityName(authorityName);
                targetBaseTemp.setProductName(productName);
                targetBaseTemp.setModuleName(moduleName);
                targetBaseTemp.setContractNo(contractNo);
                list.add(targetBaseTemp);
            }
            System.out.println("查询temp成功：共" + list.size() + "条！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getConnection(){
        try{
            Class.forName(driverStr);
            connection = DriverManager.getConnection(url+dataBaseName,userName,pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws SQLException {
        String a = "金格iSignature电子签章系统V8.2";
        String b = "金格iSignature电子签章系统V8.2";
        System.out.println(a.equals(b));
    }

    public List<Map<String,Object>> queryList(String sql){
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String clientName = resultSet.getString(1);
                Map<String,Object> map = new HashMap<>();
                map.put("clientName",clientName);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close() throws SQLException {
        preparedStatement.close();
        connection.close();
        System.out.println("Connection close");
    }


}
