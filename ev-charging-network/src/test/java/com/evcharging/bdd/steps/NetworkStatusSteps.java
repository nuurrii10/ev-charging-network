package com.evcharging.bdd.steps;

import com.evcharging.domain.Charger;
import com.evcharging.domain.ChargerManager;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class NetworkStatusSteps {

    private final ChargerManager chargerManager = new ChargerManager();

    @Given("network has charger {string} of type {string} at location {string} with status {string}")
    public void network_has_charger(String chargerId, String type, String locationName, String status) {
        chargerManager.addCharger(chargerId, type, locationName, status);
        Charger c = chargerManager.findById(chargerId);
        assertNotNull(c);
        assertEquals(status, c.getNetworkStatus());
    }

    @When("I request the network status overview")
    public void i_request_the_network_status_overview() {
        
    }

    @Then("the overview should show charger {string} status {string}")
    public void the_overview_should_show(String chargerId, String expectedStatus) {
        Charger c = chargerManager.findById(chargerId);
        assertNotNull(c);
        assertEquals(expectedStatus, c.getNetworkStatus());
    }

    @Then("the overview should contain {int} chargers")
    public void the_overview_should_contain(Integer expected) {
        assertEquals(expected.intValue(), chargerManager.count());
    }
}
