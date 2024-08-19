package com.ubs.cpt.infra.spring.config;

import com.ubs.cpt.infra.spring.datasource.DatasourceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The configuration classes for the root application context.
 * All the components are scanner using this config except spring REST controllers.
 * REST controllers are scanned using {@link DispatcherConfig}
 *
 * @author Amar Pandav
 */
@Configuration

//scan service package because - we have spring service in that package in module serviceLib
//scan infra package because - we have SpringProfiles class in infraLib which needs the scanning.
@ComponentScan({"com.ubs.cpt.service", "com.ubs.cpt.infra"})

@EnableTransactionManagement /*replacement for <tx:annotation-driven/>*/
@Import(DatasourceConfiguration.class)
public class AppConfig {
}
