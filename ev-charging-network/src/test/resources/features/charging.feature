Feature: Vehicle Charging
  As a Client
  I want to start and finish charging
  So that I can charge my vehicle and pay correctly

  Scenario: Start charging reduces available balance only after stop
    Given a charging location "City Center" exists with AC price 0.40 and DC price 0.60
    And a charging client "U1" exists with balance 20
    And a charging charger "C1" of type "AC" at location "City Center" is "ONLINE"
    When charging client "U1" starts a charging session at charger "C1"
    And the charging session consumes 10 kWh
    And charging client "U1" stops the charging session
    Then charging client "U1" should have balance 16

  Scenario: Cannot start charging when charger is OFFLINE
    Given a charging location "City Center" exists with AC price 0.40 and DC price 0.60
    And a charging client "U1" exists with balance 20
    And a charging charger "C2" of type "DC" at location "City Center" is "OFFLINE"
    When charging client "U1" starts a charging session at charger "C2"
    Then the charging session start should be rejected with reason "CHARGER_OFFLINE"

  Scenario: Charging is rejected when prepaid balance is zero
    Given a charging location "City Center" exists with AC price 0.40 and DC price 0.60
    And a charging client "U1" exists with balance 0
    And a charging charger "C1" of type "AC" at location "City Center" is "ONLINE"
    When charging client "U1" starts a charging session at charger "C1"
    Then the charging session start should be rejected with reason "INSUFFICIENT_BALANCE"
