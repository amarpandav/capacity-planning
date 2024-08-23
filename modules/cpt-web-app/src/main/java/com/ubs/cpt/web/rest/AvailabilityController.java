package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.UserAvailabilityService;
import com.ubs.cpt.service.dto.UserAvailableCapacityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final UserAvailabilityService service;

    public AvailabilityController(UserAvailabilityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<UserAvailabilityResponse> getAvailability() {
        return ResponseEntity.ok(new UserAvailabilityResponse(service.getUsersAvailability()));
    }

    record UserAvailabilityResponse(List<UserAvailableCapacityDto> userAvailability) {}
}
