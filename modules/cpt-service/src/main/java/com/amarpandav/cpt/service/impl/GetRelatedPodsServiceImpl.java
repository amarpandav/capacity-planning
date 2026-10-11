package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.service.GetRelatedPodsService;
import com.amarpandav.cpt.service.dto.PodDto;
import com.amarpandav.cpt.service.impl.mapper.PodMapper;
import com.amarpandav.cpt.service.repository.PodRepository;
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
