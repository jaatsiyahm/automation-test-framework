Feature: DummyAPI User Management
  As a tester, i want to checking the CRUD management user

  Background:
    Given API base URL "https://dummyapi.io/data/v1"
    And API using app-id "63a804408eb0cb069b57e43a"

  # ============ GET USER BY ID ============

  @api @get_user
  Scenario: Getting user info with valid ID
    Given I have user ID "60d0fe4f5311236168a109f5"
    When i run GET request to endpoint "/user/{id}"
    Then response status code should be 200
    And response body should contain field "id"
    And response body should contain field "firstName"
    And response body should contain field "lastName"
    And response body should contain field "email"

  @api @get_user_invalid
  Scenario: Getting user info with invalid ID
    Given I have user ID "invalid-id-12345"
    When i run GET request to endpoint "/user/{id}"
    Then response status code should be 400

  # ============ CREATE USER ============

  @api @create_user
  Scenario: Creating new user with valid data
    Given I have new user info:
      | firstName | lastName | email                        |
      | John      | Doe      | johndoe.test@example.com     |
    When i run POST request to endpoint "/user/create"
    Then response status code should be 200
    And response body should contain field "id"
    And response body field "firstName" should filled with "John"
    And response body field "lastName" should filled with "Doe"

  @api @create_user_invalid
  Scenario: Creating new user with used email
    Given I have user with used email
    When i run POST request to endpoint "/user/create"
    Then response status code should be 400

  # ============ UPDATE USER ============

  @api @update_user
  Scenario: Updating existing user
    Given I have ID of last user that just created
    And I have user with used email:
      | firstName | lastName  |
      | UpdatedFn | UpdatedLn |
    When I run PUT request to endpoint "/user/{id}"
    Then response status code should be 200
    And response body field "firstName" should filled with "UpdatedFn"

  # ============ DELETE USER ============

  @api @delete_user
  Scenario: Deleting existing user
    Given I have ID of last user that just created
    When I run DELETE request to endpoint "/user/{id}"
    Then response status code should be 200

  # ============ GET TAGS ============

  @api @get_tags
  Scenario: Getting List of all tags
    When i run GET request to endpoint "/tag"
    Then response status code should be 200
    And response body should contain field "data"
    And response body field "data" should be in array type