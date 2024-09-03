package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;

public record PodDto (
        EntityId<Pod> entityId,
        String podName,
        String podShortName,
        String podDescription,
        String podStyleClass
) {
}
