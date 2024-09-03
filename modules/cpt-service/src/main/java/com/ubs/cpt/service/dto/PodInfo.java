package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;

public record PodInfo(EntityId<Pod> entityId, String name) {
}
