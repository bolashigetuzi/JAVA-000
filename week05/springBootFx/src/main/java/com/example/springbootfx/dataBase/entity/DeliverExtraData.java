package com.example.springbootfx.dataBase.entity;

import lombok.Data;

@Data
public class DeliverExtraData {
    private String clientName;
    private String authorityClient;
    private String packageName;
    private String receiveDate;
    private String signDate;
    private String signDigest;
    private String authorityCode;
}
