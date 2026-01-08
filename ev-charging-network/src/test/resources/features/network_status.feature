Feature: Network Status
  As an Owner
  I want to view network and charger status
  So that I can monitor availability

  Scenario: Owner sees charger status overview
    Given a charger "C1" of type "AC" at location "City Center" is "ONLINE"
    And a charger "C2" of type "DC" at location "City Center" is "OFFLINE"
    When I request the network status overview
    Then the overview should show charger "C1" status "ONLINE"
    And the overview should show charger "C2" status "OFFLINE"
