package com.ubs.cpt.infra.spring.profiles;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * @author Amar Pandav
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile(value = ProfileMYSQLDBPROD.VALUE)
public @interface ProfileMYSQLDBPROD {
    public static final String VALUE = "MYSQLDBPROD";
}
