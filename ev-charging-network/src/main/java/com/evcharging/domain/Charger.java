package com.evcharging.domain;

public class Charger {

    private final String id;
    private final String type;          // AC / DC
    private final String locationName;  // City Center etc.
    private String networkStatus;       // ONLINE / OFFLINE

    public Charger(String id, String type, String locationName, String networkStatus) {
        this.id = id;
        this.type = type;
        this.locationName = locationName;
        this.networkStatus = networkStatus;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }
}
