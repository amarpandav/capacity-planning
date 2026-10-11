package com.amarpandav.cpt.service;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.service.dto.PodDto;

import java.util.List;

public interface GetRelatedPodsService {

    List<PodDto> getRelatedPods(EntityId<Pod> id);
}
