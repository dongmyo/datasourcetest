package com.dongmyo.test.datasourcetest.config;

import com.dongmyo.test.datasourcetest.datasource.LazyConnectionDataSource;
import com.dongmyo.test.datasourcetest.datasource.RoutingDataSource;
import com.dongmyo.test.datasourcetest.datasource.RoutingType;
import com.dongmyo.test.datasourcetest.helper.DataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@MapperScan(basePackages = "com.dongmyo.test.datasourcetest.mapper")
@Configuration
public class MybatisConfig {
    @Bean(name = "readOnlyDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public BasicDataSource readOnlyDataSource(DataSourceProperties properties) {
        return DataSourceCreator.createDataSource(properties, BasicDataSource.class);
    }

    @Bean(name = "transactionalDataSource")
    @ConfigurationProperties(prefix = "transactional.datasource")
    public BasicDataSource transactionalDataSource(DataSourceProperties properties) {
        return DataSourceCreator.createDataSource(properties, BasicDataSource.class);
    }

    @Bean(name = "dataSourceRoutingMap")
    public Map<Object, Object> dataSourceRoutingMap(
            @Qualifier("readOnlyDataSource") BasicDataSource readOnlyDataSource,
            @Qualifier("transactionalDataSource") BasicDataSource transactionalDataSource) {
        Map<Object, Object> map = new HashMap<>();
        map.put(RoutingType.READONLY, readOnlyDataSource);
        map.put(RoutingType.TRANSACTIONAL, transactionalDataSource);

        return map;
    }

    @Bean(name = "routingDataSource")
    public RoutingDataSource routingDataSource() {
        return new RoutingDataSource();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new LazyConnectionDataSource();
    }

}
