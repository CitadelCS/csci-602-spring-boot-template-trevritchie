Feature: Account Management
  As a user
  I want to create and retrieve accounts
  So that I can manage user accounts in the system

  Background:
    Given the application is running

  Scenario: Successfully create a new account
    When I create an account with username "testuser", password "password123", and email "test@example.com"
    Then the response status should be 201
    And the response should contain the created account details
    And the account should have a generated user ID
    And the account username should be "testuser"
    And the account email should be "test@example.com"
    And the account should have a created timestamp

  Scenario: Retrieve account by ID
    Given I have created an account with username "john_doe", password "secret456", and email "john@example.com"
    When I retrieve the account by its ID
    Then the response status should be 200
    And the response should contain the account details
    And the account username should be "john_doe"
    And the account email should be "john@example.com"

  Scenario: Retrieve account by username
    Given I have created an account with username "jane_smith", password "mypassword", and email "jane@example.com"
    When I retrieve the account by username "jane_smith"
    Then the response status should be 200
    And the response should contain the account details
    And the account username should be "jane_smith"
    And the account email should be "jane@example.com"

  Scenario: Retrieve non-existent account by ID
    When I retrieve an account by ID 99999
    Then the response status should be 404
    And the response body should be empty

  Scenario: Retrieve non-existent account by username
    When I retrieve an account by username "nonexistent_user"
    Then the response status should be 404
    And the response body should be empty

  Scenario: Create account with missing required fields
    When I attempt to create an account with incomplete data
    Then the response status should be 400

  Scenario: Create multiple accounts with unique usernames
    When I create an account with username "user1", password "pass1", and email "user1@example.com"
    And I create an account with username "user2", password "pass2", and email "user2@example.com"
    Then both accounts should be created successfully
    And each account should have a unique user ID
    And I should be able to retrieve both accounts by their usernames

  Scenario: Account creation includes timestamps
    When I create an account with username "timestamp_user", password "password", and email "timestamp@example.com"
    Then the response status should be 201
    And the account should have a "created_on" timestamp
    And the account should have a "last_login" timestamp
    And both timestamps should be recent