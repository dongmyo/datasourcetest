package com.dongmyo.test.datasourcetest.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;

public class LazyConnectionDataSource extends LazyConnectionDataSourceProxy {
    @Autowired
    @Qualifier("routingDataSource")
    @Override
    public void setTargetDataSource(DataSource targetDataSource) {
        super.setTargetDataSource(targetDataSource);
        super.afterPropertiesSet();
    }

}
