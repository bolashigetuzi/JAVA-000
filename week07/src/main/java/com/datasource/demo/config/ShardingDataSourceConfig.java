package com.datasource.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * shardingsphere和DataSourceConfigurationz只能有一个
 */
@Configuration
public class ShardingDataSourceConfig {
    @Bean
    public DataSource shardingDataSource() throws IOException, SQLException {
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(ShardingDataSourceConfig.class.getResource("/META-INF/replica-query.yaml").getFile()));
    }

    @Bean
    public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("shardingDataSource") DataSource shardingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource);
        return bean.getObject();
    }
}
