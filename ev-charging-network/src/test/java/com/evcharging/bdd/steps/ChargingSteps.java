package com.evcharging.bdd.steps;

import com.evcharging.domain.*;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class ChargingSteps {

    private final LocationManager locationManager = new LocationManager();
    private final ClientManager clientManager = new ClientManager();
    private final ChargerManager chargerManager = new ChargerManager();
    private final PricingManager pricingManager = new PricingManager();
    private final ChargingSessionManager sessionManager = new ChargingSessionManager();

    private String lastRejectionReason;

    @Given("a charging location {string} exists with AC price {double} and DC price {double}")
    public void a_charging_location_exists_with_prices(String locationName, double ac, double dc) {
        locationManager.addLocation(locationName, "ACTIVE");
        pricingManager.setPrices(locationName, ac, dc);
        assertNotNull(locationManager.findByName(locationName));
        assertNotNull(pricingManager.getPricing(locationName));
    }

    @Given("a charging client {string} exists with balance {int}")
    public void a_charging_client_exists_with_balance(String clientId, Integer balance) {
        clientManager.addClient(clientId, balance);
        assertNotNull(clientManager.findById(clientId));
    }

    @Given("a charging charger {string} of type {string} at location {string} is {string}")
    public void a_charging_charger_at_location_is(String chargerId, String type, String locationName, String status) {
        chargerManager.addCharger(chargerId, type, locationName, status);
        Charger c = chargerManager.findById(chargerId);
        assertNotNull(c);
        assertEquals(status, c.getNetworkStatus());
    }

    @When("charging client {string} starts a charging session at charger {string}")
    public void charging_client_starts_session(String clientId, String chargerId) {
        lastRejectionReason = null;

        Client client = clientManager.findById(clientId);
        assertNotNull(client);

        Charger charger = chargerManager.findById(chargerId);
        assertNotNull(charger);

        if ("OFFLINE".equalsIgnoreCase(charger.getNetworkStatus())) {
            lastRejectionReason = "CHARGER_OFFLINE";
            return;
        }

        if (client.getBalance() <= 0) {
            lastRejectionReason = "INSUFFICIENT_BALANCE";
            return;
        }

        double price = pricingManager.getPricePerKwh(charger.getLocationName(), charger.getType());
        sessionManager.startSession(clientId, charger, price);
    }

    @When("the charging session consumes {int} kWh")
    public void the_charging_session_consumes(Integer kwh) {
        sessionManager.consume(kwh);
    }

    @When("charging client {string} stops the charging session")
    public void charging_client_stops(String clientId) {
        double amount = sessionManager.stopAndGetAmount();
        Client client = clientManager.findById(clientId);
        assertNotNull(client);

        int costInt = (int) Math.round(amount);
        client.setBalance(client.getBalance() - costInt);
    }

    @Then("charging client {string} should have balance {int}")
    public void charging_client_should_have_balance(String clientId, Integer expectedBalance) {
        Client client = clientManager.findById(clientId);
        assertNotNull(client);
        assertEquals(expectedBalance.intValue(), client.getBalance());
    }

    @Then("the charging session start should be rejected with reason {string}")
    public void charging_rejected_with_reason(String expected) {
        assertEquals(expected, lastRejectionReason);
    }
}
