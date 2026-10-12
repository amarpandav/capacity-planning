package com.amarpandav.cpt.infra.exception;

import java.util.List;

/**
 * Thrown when someone who is not a pod lead of a pod tries to change that pod's assignments.
 * Carries the pod and its pod leads so the user can be told whom to talk to.
 */
public class NotPodLeadException extends RuntimeException {

    private final String podShortName;
    private final String podName;
    private final List<String> podLeadNames;

    public NotPodLeadException(String podShortName, String podName, List<String> podLeadNames) {
        super("Only pod leads of " + podShortName + " can change its assignments");
        this.podShortName = podShortName;
        this.podName = podName;
        this.podLeadNames = List.copyOf(podLeadNames);
    }

    public String getPodShortName() {
        return podShortName;
    }

    public String getPodName() {
        return podName;
    }

    public List<String> getPodLeadNames() {
        return podLeadNames;
    }
}
