package com.example.springbootfx.dataBase.entity;

import lombok.Data;

@Data
public class ProductDirectoryMap {
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 功能模块
     */
    private String moduleName;
    /**
     * 目录名称
     */
    private String directoryName;
}
