package edu.citadel.cucumber.stepdefs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

public class HealthCheckStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestContext testContext;

    // All step definitions have been moved to CommonStepDefs to avoid duplicates
    // This class is kept for potential future health-check specific steps
}