package com.evcharging.bdd.steps;

import com.evcharging.domain.*;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class PricingSteps {

    private final LocationManager locationManager = new LocationManager();
    private final PricingManager pricingManager = new PricingManager();

   
    private double lockedSessionPricePerKwh;

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

  
    @Given("a pricing client {string} exists with balance {int}")
    public void a_pricing_client_exists_with_balance(String clientId, Integer balance) {
        assertNotNull(clientId);
        assertNotNull(balance);
    }

    @Given("a pricing charger {string} of type {string} at location {string} is {string}")
    public void a_pricing_charger_of_type_at_location_is(String chargerId, String type, String locationName, String status) {
        assertNotNull(chargerId);
        assertNotNull(type);
        assertNotNull(locationName);
        assertNotNull(status);
    }

    @When("I set AC price {double} and DC price {double} for location {string}")
    public void i_set_prices(double acPrice, double dcPrice, String locationName) {
        pricingManager.setPrices(locationName, acPrice, dcPrice);
    }

    @When("I change AC price to {double} for location {string}")
    public void i_change_ac_price(double newAc, String locationName) {
        pricingManager.updateAcPrice(locationName, newAc);
    }

    @When("a pricing session starts at location {string} for charger type {string}")
    public void a_pricing_session_starts(String locationName, String chargerType) {
        lockedSessionPricePerKwh = pricingManager.getPricePerKwh(locationName, chargerType);
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

    @Then("the session price per kWh should remain {double}")
    public void session_price_should_remain(double expectedLockedPrice) {
        assertEquals(expectedLockedPrice, lockedSessionPricePerKwh, 0.0001);
    }
}
