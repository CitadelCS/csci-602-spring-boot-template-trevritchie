package edu.citadel.cucumber.stepdefs;

import edu.citadel.api.request.AccountRequestBody;
import edu.citadel.dal.model.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagementStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestContext testContext;

    @When("I create an account with username {string}, password {string}, and email {string}")
    public void iCreateAnAccountWithUsernamePasswordAndEmail(String username, String password, String email) {
        // Make username unique by appending timestamp to avoid conflicts with existing data
        String uniqueUsername = username + "_" + System.currentTimeMillis();
        String uniqueEmail = "test_" + System.currentTimeMillis() + "_" + email;

        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setUsername(uniqueUsername);
        requestBody.setPassword(password);
        requestBody.setEmail(uniqueEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AccountRequestBody> request = new HttpEntity<>(requestBody, headers);

        String url = "http://localhost:" + port + "/account";
        ResponseEntity<Account> response = restTemplate.postForEntity(url, request, Account.class);

        testContext.setAccountResponse(response);

        if (response.getBody() != null) {
            Account createdAccount = response.getBody();
            testContext.setCreatedAccount(createdAccount);
            testContext.addCreatedAccount(username, createdAccount);
        }
    }

    @Given("I have created an account with username {string}, password {string}, and email {string}")
    public void iHaveCreatedAnAccountWithUsernamePasswordAndEmail(String username, String password, String email) {
        iCreateAnAccountWithUsernamePasswordAndEmail(username, password, email);
        assertEquals(201, testContext.getAccountResponse().getStatusCode().value());
        assertNotNull(testContext.getCreatedAccount());
    }

    @When("I retrieve the account by its ID")
    public void iRetrieveTheAccountByItsID() {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getUser_id());

        String url = "http://localhost:" + port + "/account/" + createdAccount.getUser_id();
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class);
        testContext.setAccountResponse(response);
    }

    @When("I retrieve the account by username {string}")
    public void iRetrieveTheAccountByUsername(String username) {
        // Get the actual unique username that was created
        Account account = testContext.getCreatedAccounts().get(username);
        assertNotNull(account, "Account with base username '" + username + "' should exist in test context");
        String actualUsername = account.getUsername();

        String url = "http://localhost:" + port + "/account/username/" + actualUsername;
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class);
        testContext.setAccountResponse(response);
    }

    @When("I retrieve an account by ID {long}")
    public void iRetrieveAnAccountByID(long id) {
        String url = "http://localhost:" + port + "/account/" + id;
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class);
        testContext.setAccountResponse(response);
    }

    @When("I retrieve an account by username {string}")
    public void iRetrieveAnAccountByUsernameNonExistent(String username) {
        String url = "http://localhost:" + port + "/account/username/" + username;
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class);
        testContext.setAccountResponse(response);
    }

    @When("I attempt to create an account with incomplete data")
    public void iAttemptToCreateAnAccountWithIncompleteData() {
        // Create request with missing required fields
        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setUsername("testuser_" + System.currentTimeMillis());
        // Missing password and email

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AccountRequestBody> request = new HttpEntity<>(requestBody, headers);

        String url = "http://localhost:" + port + "/account";

        // Use exchange method to handle error responses properly
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        testContext.setStringResponse(response);
    }

    @Then("the response should contain the created account details")
    public void theResponseShouldContainTheCreatedAccountDetails() {
        ResponseEntity<Account> response = testContext.getAccountResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        testContext.setCreatedAccount(response.getBody());
    }

    @Then("the account should have a generated user ID")
    public void theAccountShouldHaveAGeneratedUserID() {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getUser_id());
        assertTrue(createdAccount.getUser_id() > 0);
    }

    @Then("the account username should be {string}")
    public void theAccountUsernameShouldBe(String expectedUsername) {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        // Check that username starts with the expected base username (before timestamp)
        assertTrue(createdAccount.getUsername().startsWith(expectedUsername + "_"),
            "Username should start with: " + expectedUsername + " but was: " + createdAccount.getUsername());
    }

    @Then("the account email should be {string}")
    public void theAccountEmailShouldBe(String expectedEmail) {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        // Check that email ends with the expected email (after timestamp prefix)
        assertTrue(createdAccount.getEmail().endsWith(expectedEmail),
            "Email should end with: " + expectedEmail + " but was: " + createdAccount.getEmail());
    }

    @Then("the account should have a created timestamp")
    public void theAccountShouldHaveACreatedTimestamp() {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getCreated_on());
    }

    @Then("the response should contain the account details")
    public void theResponseShouldContainTheAccountDetails() {
        ResponseEntity<Account> response = testContext.getAccountResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        Account account = response.getBody();
        assertNotNull(account.getUser_id());
        assertNotNull(account.getUsername());
        assertNotNull(account.getEmail());
    }

    @Then("the response body should be empty")
    public void theResponseBodyShouldBeEmpty() {
        ResponseEntity<Account> response = testContext.getAccountResponse();
        assertNotNull(response);
        assertNull(response.getBody());
    }

    @Then("both accounts should be created successfully")
    public void bothAccountsShouldBeCreatedSuccessfully() {
        assertEquals(2, testContext.getCreatedAccounts().size());
        for (Account account : testContext.getCreatedAccounts().values()) {
            assertNotNull(account);
            assertNotNull(account.getUser_id());
            assertTrue(account.getUser_id() > 0);
        }
    }

    @Then("each account should have a unique user ID")
    public void eachAccountShouldHaveAUniqueUserID() {
        assertTrue(testContext.getCreatedAccounts().size() >= 2);
        Account[] accounts = testContext.getCreatedAccounts().values().toArray(new Account[0]);

        for (int i = 0; i < accounts.length; i++) {
            for (int j = i + 1; j < accounts.length; j++) {
                assertNotEquals(accounts[i].getUser_id(), accounts[j].getUser_id(),
                    "Account user IDs should be unique");
            }
        }
    }

    @Then("I should be able to retrieve both accounts by their usernames")
    public void iShouldBeAbleToRetrieveBothAccountsByTheirUsernames() {
        for (String baseUsername : testContext.getCreatedAccounts().keySet()) {
            Account account = testContext.getCreatedAccounts().get(baseUsername);
            String actualUsername = account.getUsername();

            String url = "http://localhost:" + port + "/account/username/" + actualUsername;
            ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class);

            assertEquals(200, response.getStatusCode().value());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().getUsername().startsWith(baseUsername + "_"));
        }
    }

    @Then("the account should have a {string} timestamp")
    public void theAccountShouldHaveATimestamp(String timestampField) {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);

        switch (timestampField) {
            case "created_on":
                assertNotNull(createdAccount.getCreated_on());
                break;
            case "last_login":
                assertNotNull(createdAccount.getLast_login());
                break;
            default:
                fail("Unknown timestamp field: " + timestampField);
        }
    }

    @Then("both timestamps should be recent")
    public void bothTimestampsShouldBeRecent() {
        Account createdAccount = testContext.getCreatedAccount();
        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getCreated_on());
        assertNotNull(createdAccount.getLast_login());

        Instant now = Instant.now();
        Instant createdTime = createdAccount.getCreated_on().toInstant();
        Instant lastLoginTime = createdAccount.getLast_login().toInstant();

        // Check that timestamps are within the last minute (reasonable for test execution)
        assertTrue(ChronoUnit.MINUTES.between(createdTime, now) < 1,
            "Created timestamp should be recent");
        assertTrue(ChronoUnit.MINUTES.between(lastLoginTime, now) < 1,
            "Last login timestamp should be recent");
    }
}