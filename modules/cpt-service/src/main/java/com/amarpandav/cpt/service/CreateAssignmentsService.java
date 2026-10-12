package com.amarpandav.cpt.service;

import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;

public interface CreateAssignmentsService {

    /**
     * @param actingUser the user making the change; must be a pod lead of the request's pod
     */
    void execute(AssignmentsRequest request, UserKey actingUser);

}
