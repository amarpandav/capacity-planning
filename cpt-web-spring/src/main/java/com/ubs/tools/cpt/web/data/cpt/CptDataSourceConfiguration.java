package com.ubs.tools.cpt.web.data.cpt;

import com.ubs.tools.cpt.web.data.JpaConfig;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.ubs.tools.cpt.web.data.cpt.CptDataSourceConfiguration.CPT_ENTITY_MANAGER_FACTORY;
import static com.ubs.tools.cpt.web.data.cpt.CptDataSourceConfiguration.CPT_TRANSACTION_MANAGER;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories(
    basePackageClasses = CptTransactional.class,
    entityManagerFactoryRef = CPT_ENTITY_MANAGER_FACTORY,
    transactionManagerRef = CPT_TRANSACTION_MANAGER
)
public class CptDataSourceConfiguration {
    public static final String CPT_DATA_SOURCE_PROPERTIES = "cptDataSourceProperties" ;
    public static final String CPT_ENTITY_MANAGER_FACTORY = "cptEntityManagerFactory" ;
    public static final String CPT_TRANSACTION_MANAGER = "cptTransactionManager" ;
    public static final String CPT_DATA_SOURCE = "cptDataSource" ;
    public static final String CPT_PERSISTENCE_UNIT = "cptPersistenceUnit" ;
    public static final String CPT_JPA_CONFIG = "cptJpaConfig";

    @Bean(CPT_DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties(prefix = "cpt.datasource")
    public DataSourceProperties cptDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(CPT_DATA_SOURCE)
    public DataSource dataSource(@Qualifier(CPT_DATA_SOURCE_PROPERTIES) DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(CPT_JPA_CONFIG)
    @ConfigurationProperties("cpt.jpa")
    public JpaConfig cptJpaConfig() {
        return new JpaConfig();
    }

    @Primary
    @Bean(CPT_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean cptEntityManagerFactory(
        @Qualifier(CPT_DATA_SOURCE) DataSource dataSource,
        @Qualifier(CPT_JPA_CONFIG) JpaConfig cptJpaConfig,
        EntityManagerFactoryBuilder builder
    ) {
        var emfBuilder = builder.dataSource(dataSource)
                                .persistenceUnit(CPT_PERSISTENCE_UNIT)
                                .packages(CptTransactional.class)
                                .properties(cptJpaConfig.dataSourceProperties())
                                .mappingResources(cptJpaConfig.getMappingResources());

        return emfBuilder.build();
    }

    @Primary
    @Bean(name = CPT_TRANSACTION_MANAGER)
    public PlatformTransactionManager cptTransactionManager(@Qualifier(CPT_ENTITY_MANAGER_FACTORY) EntityManagerFactory cptEntityManagerFactory) {
        return new JpaTransactionManager(cptEntityManagerFactory);
    }
}