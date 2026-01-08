package com.evcharging.bdd.steps;

import com.evcharging.domain.Location;
import com.evcharging.domain.LocationManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

public class LocationSteps {

    private LocationManager locationManager;

    @Given("there are no locations in the system")
    public void there_are_no_locations_in_the_system() {
        locationManager = new LocationManager();
        locationManager.clear();
        assertEquals(0, locationManager.count());
    }

    @When("I create a new location with name {string} and status {string}")
    public void i_create_a_new_location_with_name_and_status(String name, String status) {
        locationManager.addLocation(name, status);
    }

    @Then("the system should contain {int} location")
    public void the_system_should_contain_location(Integer expectedCount) {
        assertEquals(expectedCount.intValue(), locationManager.count());
    }

    @Then("the location {string} should have status {string}")
    public void the_location_should_have_status(String name, String expectedStatus) {
        Location location = locationManager.findByName(name);
        assertNotNull("Location not found: " + name, location);
        assertEquals(expectedStatus, location.getNetworkStatus());
    }

    @Given("a location {string} with status {string} exists")
    public void a_location_with_status_exists(String name, String status) {
        locationManager = new LocationManager();
        locationManager.addLocation(name, status);
    }

    @When("I change the status of location {string} to {string}")
    public void i_change_the_status_of_location_to(String name, String newStatus) {
        locationManager.updateStatus(name, newStatus);
    }
}
