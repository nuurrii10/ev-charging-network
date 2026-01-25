Feature: Network Status
  As an Owner
  I want to view network and charger status
  So that I can monitor availability

  Scenario: Owner sees charger status overview
    Given network has charger "C1" of type "AC" at location "City Center" with status "ONLINE"
    And network has charger "C2" of type "DC" at location "City Center" with status "OFFLINE"
    When I request the network status overview
    Then the overview should show charger "C1" status "ONLINE"
    And the overview should show charger "C2" status "OFFLINE"

  Scenario: Overview works when no chargers exist
    Given there are no chargers in the system
    When I request the network status overview
    Then the overview should contain 0 chargers

