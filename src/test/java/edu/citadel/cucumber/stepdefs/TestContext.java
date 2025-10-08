package edu.citadel.cucumber.stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.citadel.dal.model.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Shared test context for Cucumber step definitions.
 * This class holds response objects and test data that need to be shared
 * between different step definition classes.
 */
@Getter
@Component
public class TestContext {

    // Getters and setters for response objects
    @Setter
    private ResponseEntity<String> stringResponse;
    @Setter
    private ResponseEntity<Account> accountResponse;

    // Getters and setters for test data
    @Setter
    private Account createdAccount;
    private final Map<String, Account> createdAccounts = new HashMap<>();

    // Utility getters
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Setter
    private StopWatch stopWatch;

    public void addCreatedAccount(String username, Account account) {
        createdAccounts.put(username, account);
    }

    // Helper method to get the current response (either string or account)
    public ResponseEntity<?> getCurrentResponse() {
        if (accountResponse != null) {
            return accountResponse;
        }
        return stringResponse;
    }
    
    // Helper method to get response status code
    public int getResponseStatusCode() {
        ResponseEntity<?> response = getCurrentResponse();
        return response != null ? response.getStatusCode().value() : -1;
    }
    
    // Clear method for test cleanup
    public void clear() {
        stringResponse = null;
        accountResponse = null;
        createdAccount = null;
        createdAccounts.clear();
        stopWatch = null;
    }
}