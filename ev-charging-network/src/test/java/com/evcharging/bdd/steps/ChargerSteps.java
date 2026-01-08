package com.evcharging.bdd.steps;

import com.evcharging.domain.Charger;
import com.evcharging.domain.ChargerManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

public class ChargerSteps {

    private ChargerManager chargerManager;

    @Given("there are no chargers in the system")
    public void there_are_no_chargers_in_the_system() {
        chargerManager = new ChargerManager();
        chargerManager.clear();
        assertEquals(0, chargerManager.count());
    }

    @When("I add a charger with id {string}, type {string} and status {string}")
    public void i_add_a_charger(String id, String type, String status) {
        chargerManager.addCharger(id, type, status);
    }

    @Then("the system should contain {int} charger")
    public void the_system_should_contain_charger(Integer expectedCount) {
        assertEquals(expectedCount.intValue(), chargerManager.count());
    }

    @Then("the charger {string} should have status {string}")
    public void the_charger_should_have_status(String id, String expectedStatus) {
        Charger charger = chargerManager.findById(id);
        assertNotNull(charger);
        assertEquals(expectedStatus, charger.getNetworkStatus());
    }

    @Given("a charger {string} with type {string} and status {string} exists")
    public void a_charger_exists(String id, String type, String status) {
        chargerManager = new ChargerManager();
        chargerManager.addCharger(id, type, status);
    }

    @When("I change the status of charger {string} to {string}")
    public void i_change_the_status_of_charger(String id, String newStatus) {
        chargerManager.updateStatus(id, newStatus);
    }
}
