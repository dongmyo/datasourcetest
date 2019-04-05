package com.dongmyo.test.datasourcetest.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Autowired(required = false)
    @Override
    public void setTargetDataSources(@Qualifier("dataSourceRoutingMap") Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (readOnly) {
            return RoutingType.READONLY;
        } else {
            return RoutingType.TRANSACTIONAL;
        }
    }

}
