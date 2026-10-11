package com.amarpandav.cpt.testdata.user;

import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;
/**
 * Testdata for {@link com.amarpandav.cpt.domain.entity.user.User}.
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

    public UserTestdata withEmployeeId(UserKey key) {
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
        public User AMAR = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withEmployeeId(new UserKey("10000001"))
                .withJobTitle("Engineering Lead")
                .withCountry("Switzerland")
                .create());

        public User LIAM = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Liam Parker")
                .withEmployeeId(new UserKey("10000002"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .create());

        public User TOBIAS = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Tobias Berg")
                .withEmployeeId(new UserKey("10000003"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .create());

        public User OLIVER = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Oliver Grant")
                .withEmployeeId(new UserKey("10000004"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User KEVIN = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Kevin Lang")
                .withEmployeeId(new UserKey("10000005"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User RACHEL = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Rachel Fox")
                .withEmployeeId(new UserKey("10000006"))
                .withJobTitle("Tech Business Analyst")
                .withCountry("Switzerland")
                .create());

        public User TIM = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Tara Stone")
                .withEmployeeId(new UserKey("10000007"))
                .withJobTitle("Project Manager")
                .withCountry("Switzerland")
                .create());

        public User HANNAH = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Hannah Reed")
                .withEmployeeId(new UserKey("10000008"))
                .withJobTitle("Engineering Head")
                .withCountry("UK")
                .create());

        public User NT = register(new com.amarpandav.cpt.testdata.user.UserTestdata()
                .withName("Nina Torres")
                .withEmployeeId(new UserKey("10000009"))
                .withJobTitle("Department Head")
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
