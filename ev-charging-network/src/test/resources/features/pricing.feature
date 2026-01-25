Feature: Location Pricing
  As an Owner
  I want to manage AC/DC prices per location
  So that pricing is transparent and can be applied to charging sessions

  Scenario: Set AC and DC price for a location
    Given a location "City Center" exists
    When I set AC price 0.39 and DC price 0.59 for location "City Center"
    Then location "City Center" should have AC price 0.39
    And location "City Center" should have DC price 0.59

  Scenario: Price is locked at session start
    Given a location "City Center" exists with AC price 0.39 and DC price 0.59
    And a pricing client "U1" exists with balance 50
    And a pricing charger "C1" of type "AC" at location "City Center" is "ONLINE"
    When a pricing session starts at location "City Center" for charger type "AC"
    And I change AC price to 0.49 for location "City Center"
    Then the session price per kWh should remain 0.39

  Scenario: Cannot set negative prices
    Given a location "City Center" exists
    When I set AC price -0.10 and DC price 0.59 for location "City Center"
    Then location "City Center" should have AC price 0.0

