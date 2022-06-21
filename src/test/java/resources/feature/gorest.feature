@SMOKE
Feature: As a user I would like to verify all CRUD requests for this application

  Background:
    Given I am on homepage of the given application


  Scenario: I create a new user on this application
    When  I send a POST request to the application using a valid payload
    Then  I should be able to create a new user
    And   I get a valid status code from the application

  Scenario: I read a newly created user information from this application
    When I send a GET request to the application to read newly created user
    Then I get a valid status code from the application
    And  I verify if the newly created user details are correct in the records

  Scenario: I update a newly created user information from this application
    When I send a PATCH request to the application using a valid payload
    Then I get a valid status code from the application
    And  I verify if the new user details are updated in the records

  Scenario: I delete a newly created user information from this application
    When I send a DELETE request to the application
    Then I get a valid status code from the application
    And  I verify if the new user record is deleted from the application