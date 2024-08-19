package com.ubs.cpt.infra.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The configuration classes for the dispatcher servlet application context.
 * We are using this special config for Spring REST controllers.
 *
 * @author Amar Pandav
 */
@Configuration
@EnableWebMvc
//@ComponentScan(includeFilters = @ComponentScan.Filter(Controller.class), useDefaultFilters = false)
@ComponentScan("com.ubs.cpt.rest")
public class DispatcherConfig {


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

}
