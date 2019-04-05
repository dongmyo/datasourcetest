package com.dongmyo.test.datasourcetest.config;

import com.dongmyo.test.datasourcetest.annotation.TransactionalRepository;
import com.dongmyo.test.datasourcetest.helper.DataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(
        basePackages = DatabaseConfigUtils.REPOSITORY_PACKAGE,
        entityManagerFactoryRef="transactionalEntityManagerFactory",
        transactionManagerRef="transactionalTransactionManager",
        includeFilters = @ComponentScan.Filter(TransactionalRepository.class)
)
@Configuration
public class TransactionalDatabaseConfig {
    @Bean(name = "transactionalDataSource")
    @ConfigurationProperties(prefix = "transactional.datasource")
    public BasicDataSource transactionalDataSource(DataSourceProperties properties) {
        return DataSourceCreator.createDataSource(properties, BasicDataSource.class);
    }

    @Bean(name = "transactionalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean transactionalEntityManagerFactory(
            @Qualifier("transactionalDataSource") DataSource dataSource) {
        return DatabaseConfigUtils.entityManagerFactoryBean("transactional", dataSource);
    }

    @Bean(name = "transactionalTransactionManager")
    public PlatformTransactionManager transactionalTransactionManager(
            @Qualifier("transactionalEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return DatabaseConfigUtils.jpaTransactionManager(entityManagerFactory.getObject());
    }

}
