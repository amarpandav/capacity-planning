package com.ubs.cpt.web.rest;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.service.UserPodService;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.PodInfo;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}")
public class UserPodController {

    private record UserPodsResponse(String uuid, Set<PodInfo> pods) {
    }

    private final UserPodService service;

    private final UserService userService;

    public UserPodController(UserPodService service, UserService userService) {
        this.service = service;
        this.userService = userService;
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

    // debugging purposes
    private record UserPodsRelationResponse(String uuid,
                                            String name,
                                            UserPodsResponse member,
                                            UserPodsResponse watcher) {
    }

    // debugging purposes
    @GetMapping("/pod-relation-pods")
    public ResponseEntity<UserPodsRelationResponse> getUsersPodRelationPods(@PathVariable("userId") String userId) {
        var user = this.userService
                .findUsers((UserSearchParameters) new UserSearchParameters().withEntityId(new EntityId<>(userId)))
                .getFirst();
        var memberOf = service.getUserPodMemberPods(new EntityId<>(userId));
        var watcherOf = service.getUserPodWatcherPods(new EntityId<>(userId));
        return ResponseEntity.ok(
                new UserPodsRelationResponse(
                        userId,
                        user.getName(),
                        new UserPodsResponse(userId, memberOf),
                        new UserPodsResponse(userId, watcherOf)
                )
        );
    }
}
