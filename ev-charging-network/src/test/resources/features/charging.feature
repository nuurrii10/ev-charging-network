Feature: Vehicle Charging
  As a Client
  I want to start and finish charging
  So that I can charge my vehicle and pay correctly

  Scenario: Start charging reduces available balance only after stop
    Given a location "City Center" exists with AC price 0.40 and DC price 0.60
    And a client "U1" exists with balance 20
    And a charger "C1" of type "AC" at location "City Center" is "ONLINE"
    When client "U1" starts a charging session at charger "C1"
    And the session consumes 10 kWh
    And client "U1" stops the charging session
    Then client "U1" should have balance 16

  Scenario: Cannot start charging when charger is OFFLINE
    Given a location "City Center" exists with AC price 0.40 and DC price 0.60
    And a client "U1" exists with balance 20
    And a charger "C2" of type "DC" at location "City Center" is "OFFLINE"
    When client "U1" starts a charging session at charger "C2"
    Then the session start should be rejected with reason "CHARGER_OFFLINE"
