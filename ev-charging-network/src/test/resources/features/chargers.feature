Feature: Manage Chargers
  As an Owner
  I want to manage chargers
  So that different vehicle types can charge

  Scenario: Add a new active AC charger
    Given there are no chargers in the system
    When I add a charger with id "C1", type "AC" and status "ACTIVE"
    Then the system should contain 1 charger
    And the charger "C1" should have status "ACTIVE"

  Scenario: Deactivate an existing charger
    Given a charger "C2" with type "DC" and status "ACTIVE" exists
    When I change the status of charger "C2" to "INACTIVE"
    Then the charger "C2" should have status "INACTIVE"

  Scenario: Cannot add charger with duplicate id
    Given there are no chargers in the system
    When I add a charger with id "C1", type "AC" and status "ACTIVE"
    And I add a charger with id "C1", type "DC" and status "ACTIVE"
    Then the system should contain 1 charger
