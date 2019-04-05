package com.dongmyo.test.datasourcetest.config;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

public class DatabaseConfigUtils {
    public static final String BASE_PACKAGE = "com.dongmyo.test.datasourcetest";
    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".entity";
    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";


    private DatabaseConfigUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            String persistenceUnitName, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName(persistenceUnitName);
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapters());
        emf.setPackagesToScan(ENTITY_PACKAGE);
        emf.setJpaProperties(jpaProperties());

        return emf;
    }

    public static JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());

        return jpaTransactionManager;

    }

    private static Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("show_sql", "true");
        properties.setProperty("format_sql", "true");
        properties.setProperty("use_sql_comments", "true");
        properties.setProperty("globally_quoted_identifiers", "true");
        properties.setProperty("temp.use_jdbc_metadata_defaults", "false");

        return properties;
    }

    private static JpaVendorAdapter jpaVendorAdapters() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        return hibernateJpaVendorAdapter;
    }

}
