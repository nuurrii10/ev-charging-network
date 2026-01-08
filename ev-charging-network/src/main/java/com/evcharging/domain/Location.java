package com.evcharging.domain;

public class Location {
    private final String name;
    private String status; // z.B. ACTIVE / INACTIVE

    public Location(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getNetworkStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
