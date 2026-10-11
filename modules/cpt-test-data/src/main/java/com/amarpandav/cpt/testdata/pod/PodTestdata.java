package com.amarpandav.cpt.testdata.pod;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodMember;
import com.amarpandav.cpt.domain.entity.pod.PodWatcher;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;

import java.util.HashSet;
import java.util.Set;

/**
 * Testdata for {@link com.amarpandav.cpt.domain.entity.pod.Pod}.
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
        public Pod ATLAS = register(new PodTestdata()
                .withPodName("ATLAS")
                .withPodShortName("ATLAS")
                .withPodDescription("Pod for project ATLAS")
                .withPodStyleClass("podStyleClass1")
                .addPodMember(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS)
                .addPodMember(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ATLAS)
                .addPodMember(PodMemberTestdata.suiteSynthetic().TOBIAS_FOR_ATLAS)
                .addPodMember(PodMemberTestdata.suiteSynthetic().RACHEL_FOR_ATLAS)
                .addPodWatcher(PodWatcherTestdata.suiteSynthetic().HANNAH_WATCHING_ATLAS_POD)
                .create());

        public Pod ORION = register(new PodTestdata()
                .withPodName("Orion Platform")
                .withPodShortName("ORION")
                .withPodDescription("Pod for project Orion Platform")
                .withPodStyleClass("podStyleClass2")
                .addPodMember(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ORION)
                .addPodMember(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ORION)
                .addPodMember(PodMemberTestdata.suiteSynthetic().OLIVER_FOR_ORION)
                .addPodMember(PodMemberTestdata.suiteSynthetic().KEVIN_FOR_ORION)
                .addPodMember(PodMemberTestdata.suiteSynthetic().TARA_FOR_ORION)
                .addPodWatcher(PodWatcherTestdata.suiteSynthetic().NT_WATCHING_ORION_POD)
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
