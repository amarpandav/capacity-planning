package com.ubs.cpt.infra.spring.profiles;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * The way to know which profile our application is running in.
 *
 * @author Amar Pandav
 */
@Service
public class SpringProfiles implements EnvironmentAware {

    private static Environment environment;

    public static boolean isHSQLDB() {
        return environment.acceptsProfiles(ProfileHSQLDB.VALUE);
    }

    public static boolean isMYSQLDBDEV() {
        return environment.acceptsProfiles(ProfileMYSQLDBDEV.VALUE);
    }

    public static boolean isMYSQLDBPROD() {
        return environment.acceptsProfiles(ProfileMYSQLDBPROD.VALUE);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
