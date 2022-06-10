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

  Scenario: I can change the limits (accountLimit/absoluteLimit) account data of the user
    Given I have a valid jwt token to get accounts by IBAN and permissions
    When I call the account/IBAN/{IBAN} post endpoint
    Then I receive a status of success of 200
    And I change the account data of the user

  Scenario: I can change the limits (accountLimit/absoluteLimit) account data of the user
    Given I have a valid jwt token to get accounts by IBAN and permissions
    When I call the account/IBAN/{IBAN} put endpoint
    Then I receive a status of success of 200
    And I change the account data of the user

  Scenario: I can get list of account based on the userId
    Given I have a valid jwt token to get accounts by userId and permissions
    When I call the account/id/{id} get endpoint
    Then I receive a status of success of 200
    And I get an list of account back (saving or normal) by userId


  Scenario: I can get single of account based on the IBAN
    Given I have a valid jwt token to get accounts by IBAN and permissions
    When I call the account/IBAN/{IBAN} get endpoint
    Then I receive a status of success of 200
    And I get a single account using the IBAN





