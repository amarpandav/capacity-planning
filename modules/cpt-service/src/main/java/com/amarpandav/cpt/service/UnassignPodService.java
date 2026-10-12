package com.amarpandav.cpt.service;

import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.UnassignPodResult;

public interface UnassignPodService {
    /**
     * @param actingUser the user making the change; must be a pod lead of the request's pod
     */
    UnassignPodResult execute(AssignmentsRequest request, UserKey actingUser);
}
