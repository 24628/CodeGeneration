Feature: the version can be retrieved

  Scenario: Post request to /login will result in jwt token
    Given I have a valid user object
    When I call the login endpoint
    Then I receive a status of 200
    And I get a JWT-token

  Scenario: Post request to /login with invalid user object
    Given I have an invalid user object
    When I call the login endpoint with invalid object
    Then I receive a message with user not found

  Scenario: Post request to /login with invalid user password
    Given I have an invalid user password
    When I call the login endpoint with invalid password
    Then I receive a message with user or password was invalid