package com.example.springbootfx.dataBase.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TargetData {
    private String id;
    /**
     * 产品id
     */
    private String productInfoId;
    /**
     * 合同id
     */
    private String contractId;
    /**
     * 发货时间
     */
    private Date createDate;
    /**
     * 采购客户名称
     */
    private String clientName;
    /**
     * 授权客户名称
     */
    private String authorityName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 功能模块
     */
    private Integer moduleNum;
    /**
     * 目录名称
     */
    private String directoryName;
    /**
     * 模块字符串拼接
     */
    private String moduleStr;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 签收依据
     */
    private String signCert;
    /**
     * 签收时间
     */
    private String signDate;
    private String contractNo;
}
