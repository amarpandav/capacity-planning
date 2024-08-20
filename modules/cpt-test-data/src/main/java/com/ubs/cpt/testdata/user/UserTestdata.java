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

    public UserTestdata withName(String name) {
        this.name = name;
        return this;
    }

    public UserTestdata withGPIN(UserKey key) {
        this.key = key;
        return this;
    }


    public User create() {
        User user = new User(name, key);
        return user;
    }


    public static class SuiteSynthetic extends TestDataSuite<User> {
        public User APPLE = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Amar Pandav")
                .withGPIN(new UserKey("AmarPandavGPIN"))
                .create());

        public User BANANA = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Thomas Dobler")
                .withGPIN(new UserKey("ThomasDoblerGPIN"))
                .create());

        public User KIWI = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Wiktor")
                .withGPIN(new UserKey("WiktorGPIN"))
                .create());

        public User ORANGE = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Kamil Lipinski")
                .withGPIN(new UserKey("KamilLipinskiGPIN"))
                .create());

        public User LEMON = register(new com.ubs.cpt.testdata.user.UserTestdata()
                .withName("Nitesh Shriya")
                .withGPIN(new UserKey("NiteshShriyanGPIN"))
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
