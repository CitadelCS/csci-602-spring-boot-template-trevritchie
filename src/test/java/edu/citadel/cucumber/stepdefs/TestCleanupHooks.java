package edu.citadel.cucumber.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cucumber hooks for test cleanup and setup.
 * These hooks run before and after each scenario to ensure clean test state.
 */
public class TestCleanupHooks {

    @Autowired
    private TestContext testContext;

    @Before
    public void beforeScenario() {
        // Clear the test context before each scenario
        // Tests use unique usernames to avoid conflicts with existing data
        testContext.clear();
    }
}