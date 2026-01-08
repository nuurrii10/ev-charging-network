package com.evcharging.domain;

import java.util.ArrayList;
import java.util.List;

public class ChargerManager {

    private final List<Charger> chargers = new ArrayList<>();

    public void clear() {
        chargers.clear();
    }

    // Part1-Kompatibilität (alte Calls bleiben möglich)
    public void addCharger(String id, String type, String status) {
        chargers.add(new Charger(id, type, "UNKNOWN", status));
    }

    // Part2: Charger gehört zu Location + Network Status
    public void addCharger(String id, String type, String locationName, String networkStatus) {
        chargers.add(new Charger(id, type, locationName, networkStatus));
    }

    public int count() {
        return chargers.size();
    }

    public Charger findById(String id) {
        return chargers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateStatus(String id, String newStatus) {
        Charger charger = findById(id);
        if (charger != null) {
            charger.setNetworkStatus(newStatus);
        }
    }
}
