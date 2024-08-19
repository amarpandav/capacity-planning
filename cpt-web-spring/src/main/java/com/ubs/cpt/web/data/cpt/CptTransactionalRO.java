package com.ubs.cpt.web.data.cpt;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Transactional(transactionManager = CptDataSourceConfiguration.CPT_TRANSACTION_MANAGER, readOnly = true)
public @interface CptTransactionalRO {
}
