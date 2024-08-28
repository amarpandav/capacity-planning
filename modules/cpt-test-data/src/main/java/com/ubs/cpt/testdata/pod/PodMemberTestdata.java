package com.ubs.cpt.testdata.pod;

import com.ubs.cpt.domain.entity.pod.PodMember;
import com.ubs.cpt.domain.entity.pod.PodMemberRole;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.user.UserTestdata;

/**
 * Testdata for {@link com.ubs.cpt.domain.entity.pod.PodMember}.
 *
 * @author Amar Pandav
 */
public class PodMemberTestdata {

   private User user;

    private PodMemberRole podMemberRole;


    public PodMemberTestdata withUser(User user) {
        this.user = user;
        return this;
    }

    public PodMemberTestdata withPodMemberRole(PodMemberRole podMemberRole) {
        this.podMemberRole = podMemberRole;
        return this;
    }

    public PodMember create() {
        PodMember pm = new PodMember(user, podMemberRole);
        return pm;
    }

    public static class SuiteSynthetic extends TestDataSuite<PodMember> {
        public PodMember AMAR_FOR_AURA = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withPodMemberRole(PodMemberRole.SOLUTIONS_ARCHITECT)
                .create());

        public PodMember THOMAS_FOR_AURA = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().THOMAS)
                .withPodMemberRole(PodMemberRole.JAVA_DEVELOPER)
                .create());

        public PodMember WIKTOR_FOR_GIM = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().WIKTOR)
                .withPodMemberRole(PodMemberRole.JAVA_DEVELOPER)
                .create());

        public PodMember KAMIL_FOR_GIM = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().KAMIL)
                .withPodMemberRole(PodMemberRole.NET_DEVELOPER)
                .create());

        public PodMember NITESH_FOR_AURA = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().NITESH)
                .withPodMemberRole(PodMemberRole.POD_LEAD)
                .create());

        public PodMember TIM_FOR_GIM = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().TIM)
                .withPodMemberRole(PodMemberRole.POD_LEAD)
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
