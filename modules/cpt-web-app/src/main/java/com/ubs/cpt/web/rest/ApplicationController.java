package com.ubs.cpt.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Value("${application.version}")
    private String applicationVersion;

    @GetMapping("/version")
    public ApplicationDto getApplicationVersion(){
        return new ApplicationDto(applicationVersion);
    }


    record ApplicationDto(String version) {
    }
}
