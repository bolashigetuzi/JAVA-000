package com.datasource.demo.entity;

public enum DataSourceType {
    MASTER("master"),SLAVE("slave");
    private DataSourceType(String value) {
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
