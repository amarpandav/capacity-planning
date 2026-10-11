package com.amarpandav.cpt.testdata.pod;

import com.amarpandav.cpt.domain.entity.pod.PodWatcher;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;
import com.amarpandav.cpt.testdata.user.UserTestdata;

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
        public PodWatcher HANNAH_WATCHING_ATLAS_POD = register(new PodWatcherTestdata()
                .withUser(UserTestdata.suiteSynthetic().HANNAH)
                .create());

        public PodWatcher NT_WATCHING_ORION_POD = register(new PodWatcherTestdata()
                .withUser(UserTestdata.suiteSynthetic().NT)
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
