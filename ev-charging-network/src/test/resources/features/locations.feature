Feature: Manage Locations
  As an Owner
  I want to manage charging locations
  So that I can control where charging is available

  Scenario: Add a new active location
    Given there are no locations in the system
    When I create a new location with name "City Center" and status "ACTIVE"
    Then the system should contain 1 location
    And the location "City Center" should have status "ACTIVE"

  Scenario: Deactivate an existing location
    Given a location "Highway Exit" with status "ACTIVE" exists
    When I change the status of location "Highway Exit" to "INACTIVE"
    Then the location "Highway Exit" should have status "INACTIVE"