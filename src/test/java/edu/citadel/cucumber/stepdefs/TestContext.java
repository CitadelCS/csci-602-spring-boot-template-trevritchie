package edu.citadel.cucumber.stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.citadel.dal.model.Account;
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
@Component
public class TestContext {
    
    // Response objects
    private ResponseEntity<String> stringResponse;
    private ResponseEntity<Account> accountResponse;
    
    // Test data
    private Account createdAccount;
    private final Map<String, Account> createdAccounts = new HashMap<>();
    
    // Utilities
    private final ObjectMapper objectMapper = new ObjectMapper();
    private StopWatch stopWatch;
    
    // Getters and setters for response objects
    public ResponseEntity<String> getStringResponse() {
        return stringResponse;
    }
    
    public void setStringResponse(ResponseEntity<String> stringResponse) {
        this.stringResponse = stringResponse;
    }
    
    public ResponseEntity<Account> getAccountResponse() {
        return accountResponse;
    }
    
    public void setAccountResponse(ResponseEntity<Account> accountResponse) {
        this.accountResponse = accountResponse;
    }
    
    // Getters and setters for test data
    public Account getCreatedAccount() {
        return createdAccount;
    }
    
    public void setCreatedAccount(Account createdAccount) {
        this.createdAccount = createdAccount;
    }
    
    public Map<String, Account> getCreatedAccounts() {
        return createdAccounts;
    }
    
    public void addCreatedAccount(String username, Account account) {
        createdAccounts.put(username, account);
    }
    
    // Utility getters
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    public StopWatch getStopWatch() {
        return stopWatch;
    }
    
    public void setStopWatch(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
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