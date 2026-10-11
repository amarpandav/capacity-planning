package com.amarpandav.cpt.testdata.pod;

import com.amarpandav.cpt.domain.entity.pod.PodMember;
import com.amarpandav.cpt.domain.entity.pod.PodMemberRole;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;
import com.amarpandav.cpt.testdata.user.UserTestdata;

/**
 * Testdata for {@link com.amarpandav.cpt.domain.entity.pod.PodMember}.
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
        public PodMember AMAR_FOR_ATLAS = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withPodMemberRole(PodMemberRole.POD_LEAD)
                .create());

        public PodMember AMAR_FOR_ORION = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withPodMemberRole(PodMemberRole.SOLUTIONS_ARCHITECT)
                .create());

        public PodMember LIAM_FOR_ATLAS = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().LIAM)
                .withPodMemberRole(PodMemberRole.SOLUTIONS_ARCHITECT)
                .create());

        public PodMember LIAM_FOR_ORION = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().LIAM)
                .withPodMemberRole(PodMemberRole.JAVA_DEVELOPER)
                .create());

        public PodMember TOBIAS_FOR_ATLAS = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().TOBIAS)
                .withPodMemberRole(PodMemberRole.JAVA_DEVELOPER)
                .create());

        public PodMember OLIVER_FOR_ORION = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().OLIVER)
                .withPodMemberRole(PodMemberRole.JAVA_DEVELOPER)
                .create());

        public PodMember KEVIN_FOR_ORION = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().KEVIN)
                .withPodMemberRole(PodMemberRole.NET_DEVELOPER)
                .create());

        public PodMember RACHEL_FOR_ATLAS = register(new PodMemberTestdata()
                .withUser(UserTestdata.suiteSynthetic().RACHEL)
                .withPodMemberRole(PodMemberRole.BUSINESS_ANALYSTS)
                .create());

        public PodMember TARA_FOR_ORION = register(new PodMemberTestdata()
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
