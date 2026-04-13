Feature: SauceDemo Login and Shopping
  As a buyer, i want to login and buying product

  Background:
    Given I open web "https://www.saucedemo.com"

  # ============ LOGIN ============

  @web @login @login_success
  Scenario: Login Succesful with Valid Username and Password
    When I type on username "standard_user"
    And I type on password "secret_sauce"
    And I click Login button
    Then directed to products page
    And Page should contain text "Products"

  @web @login @login_failed
  Scenario: Login failed with invalid password
    When I type on username "standard_user"
    And I type on password "wrong_password"
    And I click Login button
    Then Page should shows error message
    And Error message should contain "Username and password do not match"

  @web @login @login_locked
  Scenario: Login with locked account (locked out user)
    When I type on username "locked_out_user"
    And I type on password "secret_sauce"
    And I click Login button
    Then Page should shows error message
    And Error message should contain "Sorry, this user has been locked out"

  # ============ PRODUCT ============

  @web @product @add_to_cart
  Scenario: Adding products to cart
    Given Logged in as "standard_user" with password "secret_sauce"
    When Click product Sauce Labs Backpack
    And Click "Add to cart" button
    Then Cart quantity should be 1

  @web @product @sort
  Scenario: Sorting product based on the price ascending
    Given Logged in as "standard_user" with password "secret_sauce"
    When Sort option with "Price (low to high)"
    Then Product should be sorted in low to high price

  # ============ CHECKOUT ============

  @web @checkout
  Scenario: Checkout
    Given Logged in as "standard_user" with password "secret_sauce"
    And Added product to cart
    When Click cart icon
    And Click "Checkout" button
    And inputting shipment information:
      | firstName | lastName | zipCode |
      | Test      | User     | 12345   |
    And Click "Continue" button
    And Click "Finish" button
    Then Page should contain text "Thank you for your order!"

  # ============ LOGOUT ============

  @web @logout
  Scenario: Logout
    Given Logged in as "standard_user" with password "secret_sauce"
    When Click hamburger menu
    And Click "logout" button
    Then Directed to login page