package com.example.springbootfx.dataBase.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryData {
    private String id;
    private String contractId;
    private Date createDate;
    private String productInfoId;
    private String clientName;
    private String contractNo;
    private String authorityName;
    private String productName;
    private String signDate;
    private String packageName;
    private String signCert;
}
