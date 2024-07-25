package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.web.data.AuraDataSourceConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Transactional(transactionManager = AuraDataSourceConfiguration.AURA_TRANSACTION_MANAGER, readOnly = true)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuraTransactionalRO {
}
