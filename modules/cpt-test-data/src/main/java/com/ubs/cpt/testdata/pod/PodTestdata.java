package com.ubs.cpt.testdata.pod;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodMember;
import com.ubs.cpt.domain.entity.pod.PodWatcher;
import com.ubs.cpt.infra.test.base.TestDataSuite;

import java.util.HashSet;
import java.util.Set;

/**
 * Testdata for {@link com.ubs.cpt.domain.entity.pod.Pod}.
 *
 * @author Amar Pandav
 */
public class PodTestdata {

    private String podName;
    private String podShortName;
    private String podDescription;
    private String podStyleClass;
    private Set<PodMember> podMembers = new HashSet<>();
    private Set<PodWatcher> podWatchers= new HashSet<>();


    public PodTestdata withPodName(String podName) {
        this.podName = podName;
        return this;
    }
    public PodTestdata withPodShortName(String podShortName) {
        this.podShortName = podShortName;
        return this;
    }

    public PodTestdata withPodDescription(String podDescription) {
        this.podDescription = podDescription;
        return this;
    }

    public PodTestdata withPodStyleClass(String podStyleClass) {
        this.podStyleClass = podStyleClass;
        return this;
    }

    public PodTestdata addPodMember(PodMember podMember) {
        this.podMembers.add(podMember);
        return this;
    }

    public PodTestdata addPodWatcher(PodWatcher podWatcher) {
        this.podWatchers.add(podWatcher);
        return this;
    }

    public Pod create() {

        Pod pm = new Pod(podName, podShortName, podDescription, podStyleClass);
        for (PodMember podMember : podMembers) {
            pm.addPodMembers(podMember);;
        }

        for (PodWatcher podWatcher : podWatchers) {
            pm.addPodWatcher(podWatcher);;
        }

        return pm;
    }

    public static class SuiteSynthetic extends TestDataSuite<Pod> {
        public Pod AURA = register(new PodTestdata()
                .withPodName("AURA")
                .withPodShortName("AURA")
                .withPodDescription("Pod for project AURA")
                .withPodStyleClass("podStyleClass1")
                .addPodMember(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA)
                .addPodMember(PodMemberTestdata.suiteSynthetic().LUKASZ_FOR_AURA)
                .addPodMember(PodMemberTestdata.suiteSynthetic().THOMAS_FOR_AURA)
                .addPodMember(PodMemberTestdata.suiteSynthetic().ROLAND_FOR_AURA)
                .addPodWatcher(PodWatcherTestdata.suiteSynthetic().HRISHI_WATCHING_AURA_POD)
                .create());

        public Pod GIM = register(new PodTestdata()
                .withPodName("Global Identity Management")
                .withPodShortName("GIM")
                .withPodDescription("Pod for project Global Identity Management")
                .withPodStyleClass("podStyleClass2")
                .addPodMember(PodMemberTestdata.suiteSynthetic().AMAR_FOR_GIM)
                .addPodMember(PodMemberTestdata.suiteSynthetic().WIKTOR_FOR_GIM)
                .addPodMember(PodMemberTestdata.suiteSynthetic().KAMIL_FOR_GIM)
                .addPodMember(PodMemberTestdata.suiteSynthetic().TIM_FOR_GIM)
                .addPodWatcher(PodWatcherTestdata.suiteSynthetic().NT_WATCHING_GIM_POD)
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
