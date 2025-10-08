Feature: Application Info Endpoint
  As a developer or system administrator
  I want to retrieve application metadata
  So that I can verify the application version and details

  Scenario: Successfully retrieve application information
    Given the application is running
    When I send a GET request to "/info"
    Then the response status should be 200
    And the response should contain "name" field
    And the response should contain "version" field
    And the response should contain "description" field
    And the response content type should be "application/json"

  Scenario: Application info contains expected metadata structure
    Given the application is running
    When I send a GET request to "/info"
    Then the response should be valid JSON
    And the response should have exactly 3 fields
    And the response "name" field should not be empty
    And the response "version" field should not be empty
    And the response "description" field should not be empty

  Scenario: Application info endpoint is accessible
    Given the application is running
    When I send a GET request to "/info"
    Then the response status should be 200
    And the response time should be less than 1000 milliseconds