package com.amarpandav.cpt.service;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.service.dto.PodDto;

import java.util.List;

public interface MyPodService {

    List<PodDto> getMyPodMemberPods(EntityId<User> userId);
    List<PodDto> getMyPodWatcherPods(EntityId<User> userId);
}
