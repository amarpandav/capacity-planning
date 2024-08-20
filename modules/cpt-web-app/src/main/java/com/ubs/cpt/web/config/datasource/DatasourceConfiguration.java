package com.ubs.cpt.web.config.datasource;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.infra.spring.profiles.ProfileHSQLDB;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier(BEAN_JPA_PROPERTIES) Properties jpaProperties
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(applicationContext.getBean(BEAN_DATA_SOURCE, DataSource.class));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        em.setPackagesToScan(BaseEntity.class.getPackageName()); //Entity(DB tables) classes to scan

        em.setJpaDialect(new HibernateJpaDialect());
        em.setJpaProperties(jpaProperties);

        return em;
    }

    // ------------------------------------------ Enable annotation-driven transaction management capability for Spring

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
