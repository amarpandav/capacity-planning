package com.ubs.cpt.service;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.dto.PodDto;

import java.util.List;

public interface MyPodService {

    List<PodDto> getMyPodMemberPods(EntityId<User> userId);
    List<PodDto> getMyPodWatcherPods(EntityId<User> userId);
}
