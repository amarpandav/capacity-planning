package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.service.GetAllVisiblePodsService;
import com.ubs.cpt.service.dto.PodDto;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllVisiblePodsServiceImpl implements GetAllVisiblePodsService {

    private final PodRepository repository;

    public GetAllVisiblePodsServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PodDto> getAllVisiblePods(EntityId<Pod> id) {
        return repository.findAllVisiblePods(id.getUuid())
                .stream()
                .map(PodMapper::map)
                .toList();
    }
}
