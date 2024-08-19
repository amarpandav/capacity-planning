package com.ubs.cpt.testdata.user;

import com.ubs.cpt.domain.entity.User;
import com.ubs.cpt.infra.test.base.TestDataSuite;
/**
 * Testdata for {@link com.ubs.cpt.domain.entity.User}.
 *
 * @author Amar Pandav
 */
public class UserTestdata {

   private String name;

    private String gpin;

    public UserTestdata withName(String name) {
        this.name = name;
        return this;
    }

    public UserTestdata withGPIN(String gpin) {
        this.gpin = gpin;
        return this;
    }


    public User create() {
        User user = new User(name, gpin);
        return user;
    }


    public static class SuiteSynthetic extends TestDataSuite<User> {
        public User APPLE = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withGPIN("AmarPandavGPIN")
                .create());

        public User BANANA = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Thomas Dobler")
                .withGPIN("ThomasDoblerGPIN")
                .create());

        public User KIWI = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Wiktor")
                .withGPIN("WiktorGPIN")
                .create());

        public User ORANGE = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Kamil Lipinski")
                .withGPIN("KamilLipinskiGPIN")
                .create());

        public User LEMON = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Nitesh Shriya")
                .withGPIN("NiteshShriyanGPIN")
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
