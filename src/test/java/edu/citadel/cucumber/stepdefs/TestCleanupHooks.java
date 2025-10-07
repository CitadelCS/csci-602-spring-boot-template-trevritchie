package edu.citadel.cucumber.stepdefs;

import edu.citadel.dal.AccountRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cucumber hooks for test cleanup and setup.
 * These hooks run before and after each scenario to ensure clean test state.
 */
public class TestCleanupHooks {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestContext testContext;

    @Before
    public void beforeScenario() {
        // Clear the test context before each scenario
        testContext.clear();
    }

    @After
    @Transactional
    public void afterScenario() {
        // Clean up database after each scenario to prevent state leakage
        try {
            accountRepository.deleteAll();
        } catch (Exception e) {
            // Log but don't fail the test if cleanup fails
            System.err.println("Warning: Failed to clean up database after scenario: " + e.getMessage());
        }
        
        // Clear the test context after each scenario
        testContext.clear();
    }
}