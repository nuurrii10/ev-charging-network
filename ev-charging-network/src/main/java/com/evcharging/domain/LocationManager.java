package com.evcharging.domain;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private final List<Location> locations = new ArrayList<>();

    public void clear() {
        locations.clear();
    }

    public void addLocation(String name, String status) {
        locations.add(new Location(name, status));
    }

    public int count() {
        return locations.size();
    }

    public Location findByName(String name) {
        return locations.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateStatus(String name, String newStatus) {
        Location location = findByName(name);
        if (location != null) {
            location.setStatus(newStatus);
        }
    }
}
