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
    public void afterScenario() {
        // Clear the test context after each scenario
        testContext.clear();

        // Note: We don't clean up the database to avoid affecting production data
        // Tests use unique usernames to prevent conflicts with existing data
    }
}