package com.ubs.cpt.infra.spring.datasource;

import com.ubs.cpt.infra.spring.profiles.ProfileHSQLDB;
import com.ubs.cpt.infra.spring.profiles.ProfileMYSQLDBDEV;
import com.ubs.cpt.infra.spring.profiles.ProfileMYSQLDBPROD;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * <p>Configuration class that produces bean definitions for data sources. Depending on the datasource
 * that you specify (via command line parameters) a different data source will be produced.</p>
 */
@Slf4j
@Configuration
public class DatasourceConfiguration {

    public static final String BEAN_JPA_PROPERTIES = "myJpaProperties";
    public static final String BEAN_DATA_SOURCE = "myDataSource";

    @Autowired
    ApplicationContext applicationContext;

    // ------------------------------------------ HSQLDB Factory methods
    @Bean(name = BEAN_JPA_PROPERTIES)
    @ProfileHSQLDB
    public Properties hsqldbJpaProperties() {
        return Datasource.HSQLDB.getProperties();
    }


    @Bean(name = BEAN_DATA_SOURCE)
    @ProfileHSQLDB
    public DataSource hsqldbMemoryDataSourceCore() {
        log.info("Inside hsqldbMemoryDataSourceCore ");
        return Datasource.HSQLDB.getDataSource();
    }

    @Bean(name = BEAN_JPA_PROPERTIES)
    @ProfileMYSQLDBDEV
    public Properties mysqldbDevJpaProperties() {
        return Datasource.MYSQL_DEV.getProperties();
    }


    @Bean(name = BEAN_DATA_SOURCE)
    @ProfileMYSQLDBDEV
    public DataSource mysqldbDevDataSourceCore() {
        log.info("Inside mysqldbDevDataSourceCore ");
        return Datasource.MYSQL_DEV.getDataSource();
    }

    @Bean(name = BEAN_JPA_PROPERTIES)
    @ProfileMYSQLDBPROD
    public Properties mysqldbProdJpaProperties() {
        return Datasource.MYSQL_PROD.getProperties();
    }


    @Bean(name = BEAN_DATA_SOURCE)
    @ProfileMYSQLDBPROD
    public DataSource mysqldbProdDataSourceCore() {
        log.info("Inside mysqldbProdDataSourceCore ");
        return Datasource.MYSQL_PROD.getDataSource();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(applicationContext.getBean(BEAN_DATA_SOURCE, DataSource.class));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        em.setPackagesToScan(new String[]{"com.ubs.cpt.domain.entity"}); //Entity(DB tables) classes to scan

        em.setJpaDialect(new HibernateJpaDialect());
        em.setJpaProperties(applicationContext.getBean(BEAN_JPA_PROPERTIES, Properties.class));

        return em;
    }

    // ------------------------------------------ Enable annotation-driven transaction management capability for Spring

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
