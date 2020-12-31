package com.example.springbootfx.dataBase.entity;

import lombok.Data;

@Data
public class OriginalDataView {
    /**
     * 发货信息id
     */
    private String id;
    /**
     * 产品信息id
     */
    private String productInfoId;
    /**
     * 发货时间
     */
    private String createTime;
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
    private String moduleName;
    /**
     * 发货包名称
     */
    private String softwarePackageName;
    /**
     * 签收依据
     */
    private String signCert;
}
