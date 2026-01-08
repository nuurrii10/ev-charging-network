package com.evcharging.bdd.steps;

import com.evcharging.domain.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class PricingSteps {


    private final LocationManager locationManager = new LocationManager();
    private final ClientManager clientManager = new ClientManager();
    private final ChargerManager chargerManager = new ChargerManager();
    private final PricingManager pricingManager = new PricingManager();
    private final ChargingSessionManager sessionManager = new ChargingSessionManager();
    private final InvoiceManager invoiceManager = new InvoiceManager();


    private double lockedSessionPricePerKwh;
    private String lastRejectionReason;
    private String lastInvoiceClientId;



    @Given("a location {string} exists")
    public void a_location_exists(String locationName) {
        locationManager.addLocation(locationName, "ACTIVE");
        assertNotNull(locationManager.findByName(locationName));
    }

    @Given("a location {string} exists with AC price {double} and DC price {double}")
    public void a_location_exists_with_prices(String locationName, double ac, double dc) {
        locationManager.addLocation(locationName, "ACTIVE");
        pricingManager.setPrices(locationName, ac, dc);
    }

    @When("I set AC price {double} and DC price {double} for location {string}")
    public void i_set_prices(double acPrice, double dcPrice, String locationName) {
        pricingManager.setPrices(locationName, acPrice, dcPrice);
    }

    @Then("location {string} should have AC price {double}")
    public void location_should_have_ac_price(String locationName, double expected) {
        Pricing pricing = pricingManager.getPricing(locationName);
        assertNotNull(pricing);
        assertEquals(expected, pricing.getAcPricePerKwh(), 0.0001);
    }

    @Then("location {string} should have DC price {double}")
    public void location_should_have_dc_price(String locationName, double expected) {
        Pricing pricing = pricingManager.getPricing(locationName);
        assertNotNull(pricing);
        assertEquals(expected, pricing.getDcPricePerKwh(), 0.0001);
    }

    @When("I change AC price to {double} for location {string}")
    public void i_change_ac_price(double newAc, String locationName) {
        pricingManager.updateAcPrice(locationName, newAc);
    }

    @Then("the session price per kWh should remain {double}")
    public void session_price_should_remain(double expectedLockedPrice) {
        assertEquals(expectedLockedPrice, lockedSessionPricePerKwh, 0.0001);
    }



    @Given("a client {string} exists with balance {int}")
    public void a_client_exists_with_balance(String clientId, Integer balance) {
        clientManager.addClient(clientId, balance);
        assertNotNull(clientManager.findById(clientId));
    }

    @Given("a charger {string} of type {string} at location {string} is {string}")
    public void a_charger_at_location_is(String chargerId, String type, String locationName, String status) {
        chargerManager.addCharger(chargerId, type, locationName, status);
        Charger charger = chargerManager.findById(chargerId);
        assertNotNull(charger);
        assertEquals(status, charger.getNetworkStatus());
    }



    @When("client {string} starts a charging session at charger {string}")
    public void client_starts_session(String clientId, String chargerId) {
        lastRejectionReason = null;

        Charger charger = chargerManager.findById(chargerId);
        assertNotNull(charger);

        if ("OFFLINE".equalsIgnoreCase(charger.getNetworkStatus())) {
            lastRejectionReason = "CHARGER_OFFLINE";
            return;
        }

        double price = pricingManager.getPricePerKwh(charger.getLocationName(), charger.getType());
        lockedSessionPricePerKwh = price;

        sessionManager.startSession(clientId, charger, price);
    }

    @When("the session consumes {int} kWh")
    public void the_session_consumes_k_wh(Integer kwh) {
        sessionManager.consume(kwh);
    }

    @When("client {string} stops the charging session")
    public void client_stops_the_charging_session(String clientId) {
        double amount = sessionManager.stopAndGetAmount();

        Client client = clientManager.findById(clientId);
        assertNotNull(client);


        int costInt = (int) Math.round(amount);
        client.setBalance(client.getBalance() - costInt);

        invoiceManager.createInvoice(clientId, amount);
        lastInvoiceClientId = clientId;
    }

    @Then("client {string} should have balance {int}")
    public void client_should_have_balance(String clientId, Integer expectedBalance) {
        Client client = clientManager.findById(clientId);
        assertNotNull(client);
        assertEquals(expectedBalance.intValue(), client.getBalance());
    }

    @Then("the session start should be rejected with reason {string}")
    public void the_session_start_should_be_rejected_with_reason(String expected) {
        assertEquals(expected, lastRejectionReason);
    }



    @Then("an invoice should exist for client {string} with amount {double}")
    public void an_invoice_should_exist_for_client_with_amount(String clientId, Double amount) {
        Invoice inv = invoiceManager.findLatestForClient(clientId);
        assertNotNull(inv);
        assertEquals(amount, inv.getAmount(), 0.0001);
        lastInvoiceClientId = clientId;
    }

    @Then("the invoice status should be {string}")
    public void the_invoice_status_should_be(String expectedStatus) {
        Invoice inv = invoiceManager.findLatestForClient(lastInvoiceClientId);
        assertNotNull(inv);
        assertEquals(expectedStatus, inv.getNetworkStatus());
    }

    @Given("an invoice for client {string} with amount {double} and status {string} exists")
    public void an_invoice_for_client_with_amount_and_status_exists(String clientId, Double amount, String status) {
        invoiceManager.addExisting(clientId, amount, status);
        lastInvoiceClientId = clientId;
    }

    @When("client {string} pays the invoice")
    public void client_pays_the_invoice(String clientId) {
        invoiceManager.markPaid(clientId);
        lastInvoiceClientId = clientId;
    }



    @When("I request the network status overview")
    public void i_request_the_network_status_overview() {

    }

    @Then("the overview should show charger {string} status {string}")
    public void the_overview_should_show_charger_status(String chargerId, String expectedStatus) {
        Charger charger = chargerManager.findById(chargerId);
        assertNotNull(charger);
        assertEquals(expectedStatus, charger.getNetworkStatus());
    }
}
