package com.datasource.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.datasource.demo.DynamicDataSource;
import com.datasource.demo.entity.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: yyzz
 * Date: 2020/11/30
 * Time: 17:43
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaveDataSource") DataSource slaveDataSource){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.getValue(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE.getValue(), slaveDataSource);

        AbstractRoutingDataSource routingDataSource = new DynamicDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") DataSource routingDataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(routingDataSource);
//        return bean.getObject();
//    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }
}
