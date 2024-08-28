package com.ubs.cpt.service;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.service.dto.PodDto;

import java.util.List;

public interface GetAllVisiblePodsService {

    List<PodDto> getAllVisiblePods(EntityId<Pod> id);
}
