package com.ubs.cpt.web.rest;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.service.MyPodService;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.PodDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my-pods/{userId}")
public class MyPodsController {

    private final MyPodService service;

    private final UserService userService;

    public MyPodsController(MyPodService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/my-pod-member-pods")
    public ResponseEntity<MyPodsResponse> getMyPodsAsPodMember(@PathVariable("userId") String userId) {
        List<PodDto> userPods = service.getMyPodMemberPods(new EntityId<>(userId));
        return ResponseEntity.ok(new MyPodsResponse(userId, userPods));
    }

    @GetMapping("/my-pod-watcher-pods")
    public ResponseEntity<MyPodsResponse> getMyPodsAsPodWatcher(@PathVariable("userId") String userId) {
        List<PodDto> userPods = service.getMyPodWatcherPods(new EntityId<>(userId));
        return ResponseEntity.ok(new MyPodsResponse(userId, userPods));
    }

    // debugging purposes
    @GetMapping("/my-related-pods")
    public ResponseEntity<UserPodsRelationResponse> getMyRelatedPods(@PathVariable("userId") String userId) {
        var user = this.userService
                .findUsers((UserSearchParameters) new UserSearchParameters().withEntityId(new EntityId<>(userId)))
                .getFirst();
        var memberOf = service.getMyPodMemberPods(new EntityId<>(userId));
        var watcherOf = service.getMyPodWatcherPods(new EntityId<>(userId));
        return ResponseEntity.ok(
                new UserPodsRelationResponse(
                        userId,
                        user.getName(),
                        new MyPodsResponse(userId, memberOf),
                        new MyPodsResponse(userId, watcherOf)
                )
        );
    }

    private record MyPodsResponse(String userId, List<PodDto> pods) {
    }

    // debugging purposes
    private record UserPodsRelationResponse(String uuid,
                                            String name,
                                            MyPodsResponse member,
                                            MyPodsResponse watcher) {

    }
}

