package com.ubs.cpt.web.rest;

import com.ubs.cpt.testdata.CptTestDataCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private record ApplicationResponse(String version) {
    }

    private final String applicationVersion;
    private final CptTestDataCreator cptTestDataCreator;

    public ApplicationController(@Value("${application.version}") String applicationVersion,
                                 CptTestDataCreator cptTestDataCreator) {
        this.applicationVersion = applicationVersion;
        this.cptTestDataCreator = cptTestDataCreator;
    }

    @GetMapping("/version")
    public ApplicationResponse getApplicationVersion() {
        return new ApplicationResponse(applicationVersion);
    }

    @GetMapping("/reset")
    public void deleteOldTestDate() {
        this.cptTestDataCreator.refreshTestData();
    }
}
