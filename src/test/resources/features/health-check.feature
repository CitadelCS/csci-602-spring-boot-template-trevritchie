Feature: Health Check Endpoint
  As a system administrator
  I want to check the health status of the application
  So that I can monitor if the service is running properly

  Scenario: Successfully check application health
    Given the application is running
    When I send a GET request to "/health"
    Then the response status should be 200
    And the response should contain "status" with value "ok"
    And the response content type should be "application/json"

  Scenario: Health endpoint returns consistent response format
    Given the application is running
    When I send a GET request to "/health"
    Then the response should be valid JSON
    And the response should have exactly 1 field
    And the response should contain only the "status" field