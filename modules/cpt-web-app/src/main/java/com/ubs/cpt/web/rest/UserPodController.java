package com.ubs.cpt.web.rest;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.service.UserPodService;
import com.ubs.cpt.service.dto.PodInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}")
public class UserPodController {

    private final UserPodService service;

    public UserPodController(UserPodService service) {
        this.service = service;
    }

    @GetMapping("/pod-member-pods")
    public ResponseEntity<UserPodsResponse> getUsersPodMemberPods(@PathVariable("userId") String userId) {
        Set<PodInfo> userPods = service.getUserPodMemberPods(new EntityId<>(userId));
        return ResponseEntity.ok(new UserPodsResponse(userId, userPods));
    }

    @GetMapping("/pod-watcher-pods")
    public ResponseEntity<UserPodsResponse> getUsersPodWatcherPods(@PathVariable("userId") String userId) {
        Set<PodInfo> userPods = service.getUserPodWatcherPods(new EntityId<>(userId));
        return ResponseEntity.ok(new UserPodsResponse(userId, userPods));
    }

    record UserPodsResponse(String uuid, Set<PodInfo> pods) {}
}
