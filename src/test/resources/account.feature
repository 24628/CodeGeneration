Feature: the account controller works

  Scenario: I can get an list of accounts
    Given I have a valid jwt token to get accounts and permissions
    When I call the account get endpoint
    Then I receive a status of success of 200
    And I get a list of accounts back

  Scenario:  I dont have valid jwt token
    Given I dont have valid jwt token
    When When I call an endpoint with permissions
    Then I get a message forbidden to access