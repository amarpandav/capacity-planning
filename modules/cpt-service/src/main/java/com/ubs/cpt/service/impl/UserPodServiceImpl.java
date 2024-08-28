package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.UserPodService;
import com.ubs.cpt.service.dto.PodInfo;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserPodServiceImpl implements UserPodService {

    private final PodRepository repository;

    public UserPodServiceImpl(PodRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<PodInfo> getUserPodMemberPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsMember(userId).stream()
                .map(pod -> new PodInfo(pod.getEntityId().getUuid(), pod.getPodName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PodInfo> getUserPodWatcherPods(EntityId<User> userId) {
        return repository.findPodsWhereUserIsWatcher(userId).stream()
                .map(pod -> new PodInfo(pod.getEntityId().getUuid(), pod.getPodName()))
                .collect(Collectors.toSet());
    }
}
