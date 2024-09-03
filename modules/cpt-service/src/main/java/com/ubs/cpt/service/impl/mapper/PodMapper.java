package com.ubs.cpt.service.impl.mapper;

import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.service.dto.PodDto;

import static java.util.Objects.isNull;

public class PodMapper {
    private PodMapper() {
    }

    public static PodDto map(Pod pod) {
        if (isNull(pod)) {
            return null;
        }
        return new PodDto(pod.getEntityId(),
                pod.getPodName(), pod.getPodShortName(), pod.getPodDescription(), pod.getPodStyleClass());
    }
}
