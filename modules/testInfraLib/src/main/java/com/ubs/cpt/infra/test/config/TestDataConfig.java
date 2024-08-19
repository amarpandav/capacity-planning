package com.ubs.cpt.infra.test.config;

import com.smoothie.infra.spring.config.DispatcherConfig;
import com.smoothie.infra.spring.datasource.DatasourceConfiguration;
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
@ComponentScan("com.smoothie.testdata")
@EnableTransactionManagement /*replacement for <tx:annotation-driven/>*/
@Import(DatasourceConfiguration.class)
public class TestDataConfig {
}
