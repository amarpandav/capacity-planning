package com.ubs.cpt.testdata.pod;

import com.ubs.cpt.domain.entity.pod.PodWatcher;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.user.UserTestdata;

/**
 * Testdata for {@link PodWatcher}.
 *
 * @author Amar Pandav
 */
public class PodWatcherTestdata {

   private User user;


    public PodWatcherTestdata withUser(User user) {
        this.user = user;
        return this;
    }

    public PodWatcher create() {
        PodWatcher pw = new PodWatcher(user);
        return pw;
    }

    public static class SuiteSynthetic extends TestDataSuite<PodWatcher> {
        public PodWatcher HRISHI_WATCHING_AURA_POD = register(new PodWatcherTestdata()
                .withUser(UserTestdata.suiteSynthetic().HRISHI)
                .create());

        public PodWatcher NT_WATCHING_GIM_POD = register(new PodWatcherTestdata()
                .withUser(UserTestdata.suiteSynthetic().THOMAS)
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
