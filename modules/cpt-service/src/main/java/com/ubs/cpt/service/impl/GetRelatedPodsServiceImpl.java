package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.service.GetRelatedPodsService;
import com.ubs.cpt.service.dto.PodDto;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRelatedPodsServiceImpl implements GetRelatedPodsService {

    private final PodRepository repository;

    public GetRelatedPodsServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PodDto> getRelatedPods(EntityId<Pod> id) {
        return repository.findRelatedPods(id.getUuid())
                .stream()
                .map(PodMapper::map)
                .toList();
    }
}
