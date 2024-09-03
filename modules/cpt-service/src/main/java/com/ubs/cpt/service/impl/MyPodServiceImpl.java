package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.MyPodService;
import com.ubs.cpt.service.dto.PodDto;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPodServiceImpl implements MyPodService {

    private final PodRepository repository;

    public MyPodServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PodDto> getMyPodMemberPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsMember(userId).stream()
                .map(PodMapper::map)
                .toList();
    }

    @Override
    public List<PodDto> getMyPodWatcherPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsWatcher(userId).stream()
                .map(PodMapper::map)
                .toList();
    }
}
