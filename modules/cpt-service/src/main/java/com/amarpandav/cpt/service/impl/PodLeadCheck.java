package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.exception.NotPodLeadException;

import java.util.List;

/**
 * Only a pod lead of a pod may assign users to it or remove its assignments.
 */
final class PodLeadCheck {

    private PodLeadCheck() {
    }

    static void requirePodLead(Pod pod, UserKey actingUser) {
        if (!pod.isPodLead(actingUser)) {
            throw new NotPodLeadException(pod.getPodShortName(), pod.getPodName(), podLeadNames(pod));
        }
    }

    static List<String> podLeadNames(Pod pod) {
        return pod.getPodLeads().stream().map(User::getName).toList();
    }
}
