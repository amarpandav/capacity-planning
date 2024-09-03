package com.ubs.cpt.testdata.user;

import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserKey;
import com.ubs.cpt.infra.test.base.TestDataSuite;
/**
 * Testdata for {@link com.ubs.cpt.domain.entity.user.User}.
 *
 * @author Amar Pandav
 */
public class UserTestdata {

   private String name;

    private UserKey key;

    private String jobTitle;

    private String country;

    public UserTestdata withName(String name) {
        this.name = name;
        return this;
    }

    public UserTestdata withGPIN(UserKey key) {
        this.key = key;
        return this;
    }

    public UserTestdata withJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public UserTestdata withCountry(String country) {
        this.country = country;
        return this;
    }


    public User create() {
        return new User(name, key, jobTitle, country);
    }


    public static class SuiteSynthetic extends TestDataSuite<User> {
        public User AMAR = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withGPIN(new UserKey("49008491"))
                .withJobTitle("Engineering Lead")
                .withCountry("Switzerland")
                .create());

        public User LUKASZ = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Lukasz Piotrowski")
                .withGPIN(new UserKey("49015871"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .create());

        public User THOMAS = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Thomas Dobler")
                .withGPIN(new UserKey("49041056"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .create());

        public User WIKTOR = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Wiktor Rosinski")
                .withGPIN(new UserKey("49064232"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User KAMIL = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Kamil Lipinski")
                .withGPIN(new UserKey("49063666"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User ROLAND = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Roland Fluhr")
                .withGPIN(new UserKey("49016605"))
                .withJobTitle("Tech Business Analyst")
                .withCountry("Switzerland")
                .create());

        public User TIM = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Timothy Schilling")
                .withGPIN(new UserKey("49013788"))
                .withJobTitle("Project Manager")
                .withCountry("Switzerland")
                .create());

        public User HRISHI = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Hrishikesh Raghavan")
                .withGPIN(new UserKey("43333447"))
                .withJobTitle("Engineering Head")
                .withCountry("UK")
                .create());

        public User NT = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Nick Tuffs")
                .withGPIN(new UserKey("43568763"))
                .withJobTitle("AGO Head")
                .withCountry("UK")
                .create());


    }

    private static SuiteSynthetic synthetic;

    public static SuiteSynthetic suiteSynthetic() {
        if (synthetic == null) {
            synthetic = new SuiteSynthetic();
        }
        return synthetic;
    }
}
