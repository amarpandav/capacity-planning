package com.amarpandav.cpt.service.dto;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.pod.Pod;

public record PodDto (
        EntityId<Pod> entityId,
        String podName,
        String podShortName,
        String podDescription,
        String podStyleClass
) {
}
