package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.UserUnavailableService;
import com.ubs.cpt.service.dto.UserUnavailableDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserUnavailableController {

    private final UserUnavailableService service;

    public UserUnavailableController(UserUnavailableService service) {
        this.service = service;
    }

    @GetMapping("/unavailability")
    public ResponseEntity<UserUnavailableDto> getUnavailableDates() {
        return ResponseEntity.ok(service.findUnavailableDates());
    }
}
