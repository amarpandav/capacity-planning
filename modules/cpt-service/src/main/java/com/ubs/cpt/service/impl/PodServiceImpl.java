package com.ubs.cpt.service.impl;

import com.ubs.cpt.service.PodService;
import com.ubs.cpt.service.dto.PodDto;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Debugging purposes
 */
@Service
public class PodServiceImpl implements PodService {

    private final PodRepository repository;

    public PodServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PodDto> getAllPods() {
        return repository.findAllPods()
                .stream()
                .map(PodMapper::map)
                .toList();
    }
}
