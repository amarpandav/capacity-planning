package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.MyPodService;
import com.ubs.cpt.service.dto.MyPod;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyPodServiceImpl implements MyPodService {

    private final PodRepository repository;

    public MyPodServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<MyPod> getMyPodMemberPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsMember(userId).stream()
                .map(pod -> new MyPod(pod.getEntityId(), pod.getPodName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MyPod> getMyPodWatcherPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsWatcher(userId).stream()
                .map(pod -> new MyPod(pod.getEntityId(), pod.getPodName()))
                .collect(Collectors.toSet());
    }
}
