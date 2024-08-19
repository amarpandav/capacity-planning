package com.ubs.cpt.web.data.aura;

import com.ubs.cpt.web.data.JpaConfig;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.ubs.cpt.web.data.aura.AuraDataSourceConfiguration.AURA_ENTITY_MANAGER_FACTORY;
import static com.ubs.cpt.web.data.aura.AuraDataSourceConfiguration.AURA_TRANSACTION_MANAGER;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories(
    basePackageClasses = AuraTransactional.class,
    entityManagerFactoryRef = AURA_ENTITY_MANAGER_FACTORY,
    transactionManagerRef = AURA_TRANSACTION_MANAGER
)
public class AuraDataSourceConfiguration {
    public static final String AURA_DATA_SOURCE_PROPERTIES = "auraDataSourceProperties" ;
    public static final String AURA_ENTITY_MANAGER_FACTORY = "auraEntityManagerFactory" ;
    public static final String AURA_TRANSACTION_MANAGER = "auraTransactionManager" ;
    public static final String AURA_DATA_SOURCE = "auraDataSource" ;
    public static final String AURA_PERSISTENCE_UNIT = "auraPersistenceUnit" ;
    public static final String AURA_JPA_CONFIG = "auraJpaConfig" ;

    @Bean(AURA_DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties(prefix = "aura.datasource")
    public DataSourceProperties auraDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(AURA_DATA_SOURCE)
    public DataSource dataSource(@Qualifier(AURA_DATA_SOURCE_PROPERTIES) DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @ConfigurationProperties("aura.jpa")
    @Bean(AURA_JPA_CONFIG)
    public JpaConfig auraJpaProperties() {
        return new JpaConfig();
    }

    @Bean(AURA_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean auraEntityManagerFactory(
        @Qualifier(AURA_DATA_SOURCE) DataSource dataSource,
        @Qualifier(AURA_JPA_CONFIG) JpaConfig auraJpaConfig,
        EntityManagerFactoryBuilder builder
    ) {
        var emfBuilder = builder.dataSource(dataSource)
                                .persistenceUnit(AURA_PERSISTENCE_UNIT)
                                .packages(AuraTransactional.class)
                                .properties(auraJpaConfig.dataSourceProperties())
                                .mappingResources(auraJpaConfig.getMappingResources());

        return emfBuilder.build();
    }

    @Bean(name = AURA_TRANSACTION_MANAGER)
    public PlatformTransactionManager auraTransactionManager(@Qualifier(AURA_ENTITY_MANAGER_FACTORY) EntityManagerFactory auraEntityManagerFactory) {
        return new JpaTransactionManager(auraEntityManagerFactory);
    }
}

