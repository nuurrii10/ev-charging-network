Feature: Manage Clients
  As a system user
  I want to manage client accounts
  So that charging sessions can be tracked

  Scenario: Register a new client
    Given there are no clients in the system
    When I add a client with id "U1" and balance 50
    Then the system should contain 1 client
    And the client "U1" should have balance 50

  Scenario: Update client balance
    Given a client "U2" with balance 20 exists
    When I update the balance of client "U2" to 80
    Then the client "U2" should have balance 80
