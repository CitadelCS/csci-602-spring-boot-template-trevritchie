package edu.citadel.cucumber.stepdefs;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

public class CommonStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestContext testContext;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        // Application is already running due to @SpringBootTest configuration
        // This step is mainly for BDD readability
        assertNotNull(restTemplate);
        assertTrue(port > 0);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        String url = "http://localhost:" + port + endpoint;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        stopWatch.stop();
        
        testContext.setStringResponse(response);
        testContext.setStopWatch(stopWatch);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, testContext.getResponseStatusCode());
    }

    @Then("the response content type should be {string}")
    public void theResponseContentTypeShouldBe(String expectedContentType) {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getHeaders().getContentType());
        assertTrue(response.getHeaders().getContentType().toString().contains(expectedContentType));
    }

    @Then("the response should be valid JSON")
    public void theResponseShouldBeValidJSON() throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        // This will throw an exception if the JSON is invalid
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertNotNull(jsonNode);
    }

    @Then("the response should contain {string} with value {string}")
    public void theResponseShouldContainWithValue(String fieldName, String expectedValue) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertTrue(jsonNode.has(fieldName), "Response should contain field: " + fieldName);
        assertEquals(expectedValue, jsonNode.get(fieldName).asText());
    }

    @Then("the response should contain {string} field")
    public void theResponseShouldContainField(String fieldName) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertTrue(jsonNode.has(fieldName), "Response should contain field: " + fieldName);
    }

    @Then("the response should have exactly {int} field")
    public void theResponseShouldHaveExactlyField(int expectedFieldCount) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertEquals(expectedFieldCount, jsonNode.size());
    }

    @Then("the response should have exactly {int} fields")
    public void theResponseShouldHaveExactlyFields(int expectedFieldCount) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertEquals(expectedFieldCount, jsonNode.size());
    }

    @Then("the response should contain only the {string} field")
    public void theResponseShouldContainOnlyTheField(String fieldName) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertEquals(1, jsonNode.size());
        assertTrue(jsonNode.has(fieldName), "Response should contain only the field: " + fieldName);
    }

    @Then("the response {string} field should not be empty")
    public void theResponseFieldShouldNotBeEmpty(String fieldName) throws Exception {
        ResponseEntity<String> response = testContext.getStringResponse();
        assertNotNull(response);
        assertNotNull(response.getBody());
        
        JsonNode jsonNode = testContext.getObjectMapper().readTree(response.getBody());
        assertTrue(jsonNode.has(fieldName), "Response should contain field: " + fieldName);
        
        JsonNode fieldNode = jsonNode.get(fieldName);
        assertNotNull(fieldNode);
        assertFalse(fieldNode.asText().trim().isEmpty(), "Field " + fieldName + " should not be empty");
    }

    @Then("the response time should be less than {int} milliseconds")
    public void theResponseTimeShouldBeLessThanMilliseconds(int maxResponseTime) {
        StopWatch stopWatch = testContext.getStopWatch();
        assertNotNull(stopWatch);
        long actualResponseTime = stopWatch.getTotalTimeMillis();
        assertTrue(actualResponseTime < maxResponseTime, 
            "Response time " + actualResponseTime + "ms should be less than " + maxResponseTime + "ms");
    }
}