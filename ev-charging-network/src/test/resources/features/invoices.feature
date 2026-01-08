Feature: Invoice Status
  As a Client
  I want to view invoices and invoice status
  So that I can track charges and payments

  Scenario: Invoice is created after a completed charging session
    Given a location "City Center" exists with AC price 0.40 and DC price 0.60
    And a client "U1" exists with balance 20
    And a charger "C1" of type "AC" at location "City Center" is "ONLINE"
    When client "U1" starts a charging session at charger "C1"
    And the session consumes 5 kWh
    And client "U1" stops the charging session
    Then an invoice should exist for client "U1" with amount 2.0
    And the invoice status should be "UNPAID"

  Scenario: Client can mark invoice as PAID
    Given an invoice for client "U1" with amount 2.0 and status "UNPAID" exists
    When client "U1" pays the invoice
    Then the invoice status should be "PAID"
