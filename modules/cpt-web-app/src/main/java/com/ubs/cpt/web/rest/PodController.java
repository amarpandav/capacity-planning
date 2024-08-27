package com.ubs.cpt.web.rest;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.service.GetAllVisiblePodsService;
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

    private final GetAllVisiblePodsService service;

    public PodController(GetAllVisiblePodsService service) {
        this.service = service;
    }

    @GetMapping("/{id}/visible-pods")
    public ResponseEntity<PodsResponse> getAllVisiblePods(@PathVariable("id") String podId) {
        List<PodDto> allVisiblePods = service.getAllVisiblePods(new EntityId<>(podId));
        return ResponseEntity.ok(new PodsResponse(allVisiblePods));
    }

    record PodsResponse(List<PodDto> pods) {}
}
