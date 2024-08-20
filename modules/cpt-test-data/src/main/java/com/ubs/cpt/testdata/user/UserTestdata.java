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
        User user = new User(name, key, jobTitle, country);
        return user;
    }


    public static class SuiteSynthetic extends TestDataSuite<User> {
        public User AMAR = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withGPIN(new UserKey("AmarPandavGPIN"))
                .withJobTitle("Engineering Lead")
                .withCountry("Switzerland")
                .create());

        public User THOMAS = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Thomas Dobler")
                .withGPIN(new UserKey("ThomasDoblerGPIN"))
                .withJobTitle("Software Developer")
                .withCountry("Switzerland")
                .create());

        public User WIKTOR = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Wiktor")
                .withGPIN(new UserKey("WiktorGPIN"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User KAMIL = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Kamil Lipinski")
                .withGPIN(new UserKey("KamilLipinskiGPIN"))
                .withJobTitle("Software Developer")
                .withCountry("Poland")
                .create());

        public User NITESH = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Nitesh Shriya")
                .withGPIN(new UserKey("NiteshShriyanGPIN"))
                .withJobTitle("Delivery Manager")
                .withCountry("Switzerland")
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
