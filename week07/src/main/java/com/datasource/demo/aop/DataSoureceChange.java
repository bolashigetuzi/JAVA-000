package com.datasource.demo.aop;

import com.datasource.demo.entity.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA
 * User: yyzz
 * Date: 2020/12/1
 * Time: 10:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSoureceChange {
    DataSourceType type() default DataSourceType.MASTER;
}
