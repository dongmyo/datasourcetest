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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/*
        <jpa:repositories base-package="com.dongmyo.test.datasourcetest.repository"
                          entity-manager-factory-ref="entityManagerFactory"
                          transaction-manager-ref="transactionManager"
        />
 */
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(
        basePackages = DatabaseConfigUtils.REPOSITORY_PACKAGE,
        entityManagerFactoryRef="entityManagerFactory",
        transactionManagerRef="transactionManager",
        excludeFilters = @ComponentScan.Filter(TransactionalRepository.class)
)
@Configuration
public class ReadOnlyDatabaseConfig {
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public BasicDataSource dataSource(DataSourceProperties properties) {
        return DataSourceCreator.createDataSource(properties, BasicDataSource.class);
    }

    /*
     * <bean name="entityManagerFactory" primary="true" />
     */
    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource) {
        return DatabaseConfigUtils.entityManagerFactoryBean("readOnly", dataSource);
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return DatabaseConfigUtils.jpaTransactionManager(entityManagerFactory.getObject());
    }

}
