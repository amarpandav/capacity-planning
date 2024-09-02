package com.ubs.cpt.web.rest;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.service.GetAllVisiblePodsService;
import com.ubs.cpt.service.PodService;
import com.ubs.cpt.service.dto.PodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pods")
public class PodController {

    private record PodsResponse(List<PodDto> pods) {
    }

    private final GetAllVisiblePodsService getAllVisiblePodsService;

    private final PodService podService;

    public PodController(
            GetAllVisiblePodsService getAllVisiblePodsService,
            PodService podService) {
        this.getAllVisiblePodsService = getAllVisiblePodsService;
        this.podService = podService;
    }

    @GetMapping("/{id}/visible-pods")
    public ResponseEntity<PodsResponse> getAllVisiblePods(@PathVariable("id") String podId) {
        List<PodDto> allVisiblePods = getAllVisiblePodsService.getAllVisiblePods(new EntityId<>(podId));
        return ResponseEntity.ok(new PodsResponse(allVisiblePods));
    }

    // Debugging purposes
    @GetMapping
    ResponseEntity<PodsResponse> getAllPods() {
        var allPods = podService.getAllPods();
        return ResponseEntity.ok(new PodsResponse(allPods));
    }


}
