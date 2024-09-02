package com.ubs.cpt.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/application")
public class ApplicationController {

    private record ApplicationResponse(String version) {
    }

    private final String applicationVersion;

    public ApplicationController(@Value("${application.version}") String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    @GetMapping("/version")
    public ApplicationResponse getApplicationVersion() {
        return new ApplicationResponse(applicationVersion);
    }
}
