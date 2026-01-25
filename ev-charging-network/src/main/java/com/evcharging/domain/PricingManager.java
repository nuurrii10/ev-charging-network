package com.evcharging.domain;

import java.util.HashMap;
import java.util.Map;

public class PricingManager {

    private final Map<String, Pricing> pricingByLocationName = new HashMap<>();

    public void setPrices(String locationName, double acPricePerKwh, double dcPricePerKwh) {
        double ac = Math.max(0.0, acPricePerKwh);
        double dc = Math.max(0.0, dcPricePerKwh);
        pricingByLocationName.put(locationName, new Pricing(ac, dc));
    }

    public Pricing getPricing(String locationName) {
        return pricingByLocationName.get(locationName);
    }

    public double getPricePerKwh(String locationName, String chargerType) {
        Pricing pricing = getPricing(locationName);
        if (pricing == null) return 0.0;

        if ("AC".equalsIgnoreCase(chargerType)) {
            return pricing.getAcPricePerKwh();
        }
        return pricing.getDcPricePerKwh();
    }

    public void updateAcPrice(String locationName, double newAcPrice) {
        Pricing pricing = pricingByLocationName.get(locationName);
        if (pricing != null) {
            pricing.setAcPricePerKwh(Math.max(0.0, newAcPrice));
        }
    }
}
