package com.evcharging.bdd.steps;

import com.evcharging.domain.*;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class InvoiceSteps {

    private final LocationManager locationManager = new LocationManager();
    private final ClientManager clientManager = new ClientManager();
    private final ChargerManager chargerManager = new ChargerManager();
    private final PricingManager pricingManager = new PricingManager();
    private final ChargingSessionManager sessionManager = new ChargingSessionManager();
    private final InvoiceManager invoiceManager = new InvoiceManager();

    private String lastInvoiceClientId;

    @Given("an invoicing location {string} exists with AC price {double} and DC price {double}")
    public void an_invoicing_location_exists_with_prices(String locationName, double ac, double dc) {
        locationManager.addLocation(locationName, "ACTIVE");
        pricingManager.setPrices(locationName, ac, dc);
        assertNotNull(locationManager.findByName(locationName));
        assertNotNull(pricingManager.getPricing(locationName));
    }

    @Given("an invoicing client {string} exists with balance {int}")
    public void an_invoicing_client_exists_with_balance(String clientId, Integer balance) {
        clientManager.addClient(clientId, balance);
        assertNotNull(clientManager.findById(clientId));
    }

    @Given("an invoicing charger {string} of type {string} at location {string} is {string}")
    public void an_invoicing_charger_at_location_is(String chargerId, String type, String locationName, String status) {
        chargerManager.addCharger(chargerId, type, locationName, status);
        Charger c = chargerManager.findById(chargerId);
        assertNotNull(c);
        assertEquals(status, c.getNetworkStatus());
    }

    @When("invoicing client {string} starts a charging session at charger {string}")
    public void invoicing_client_starts_session(String clientId, String chargerId) {
        Charger charger = chargerManager.findById(chargerId);
        assertNotNull(charger);

        double lockedPrice = pricingManager.getPricePerKwh(charger.getLocationName(), charger.getType());
        sessionManager.startSession(clientId, charger, lockedPrice);

        lastInvoiceClientId = clientId;
    }

    @When("the invoicing session consumes {int} kWh")
    public void the_invoicing_session_consumes(Integer kwh) {
        sessionManager.consume(kwh);
    }

    @When("invoicing client {string} stops the charging session")
    public void invoicing_client_stops_session(String clientId) {
        double amount = sessionManager.stopAndGetAmount();
        invoiceManager.createInvoice(clientId, amount);
        lastInvoiceClientId = clientId;
    }

    @Then("an invoice should exist for client {string} with amount {double}")
    public void an_invoice_should_exist(String clientId, Double amount) {
        Invoice inv = invoiceManager.findLatestForClient(clientId);
        assertNotNull(inv);
        assertEquals(amount, inv.getAmount(), 0.0001);
        lastInvoiceClientId = clientId;
    }

    @Then("no invoice should exist for client {string}")
    public void no_invoice_should_exist(String clientId) {
        Invoice inv = invoiceManager.findLatestForClient(clientId);
        assertNull(inv);
    }

    @Then("the invoice status should be {string}")
    public void the_invoice_status_should_be(String expectedStatus) {
        Invoice inv = invoiceManager.findLatestForClient(lastInvoiceClientId);
        assertNotNull(inv);
        assertEquals(expectedStatus, inv.getStatus());
    }

    @Given("an invoice for client {string} with amount {double} and status {string} exists")
    public void an_invoice_exists(String clientId, Double amount, String status) {
        invoiceManager.addExisting(clientId, amount, status);
        lastInvoiceClientId = clientId;
    }

    @When("client {string} pays the invoice")
    public void client_pays_the_invoice(String clientId) {
        invoiceManager.markPaid(clientId);
        lastInvoiceClientId = clientId;
    }
}
