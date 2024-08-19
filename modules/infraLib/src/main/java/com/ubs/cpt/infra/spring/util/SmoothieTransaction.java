package com.ubs.cpt.infra.spring.util;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this when you want to update DB.
 *
 * @author Amar Pandav
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "transactionManager", readOnly = false)
public @interface SmoothieTransaction {
}
