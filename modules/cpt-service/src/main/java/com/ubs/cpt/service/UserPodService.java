package com.ubs.cpt.service;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.dto.PodInfo;

import java.util.Set;

public interface UserPodService {

    Set<PodInfo> getUserPodMemberPods(EntityId<User> userId);
    Set<PodInfo> getUserPodWatcherPods(EntityId<User> userId);
}
