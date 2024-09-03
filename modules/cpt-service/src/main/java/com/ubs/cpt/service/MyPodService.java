package com.ubs.cpt.service;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.dto.MyPod;

import java.util.Set;

public interface MyPodService {

    Set<MyPod> getMyPodMemberPods(EntityId<User> userId);
    Set<MyPod> getMyPodWatcherPods(EntityId<User> userId);
}
