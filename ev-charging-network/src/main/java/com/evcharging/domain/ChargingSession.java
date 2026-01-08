package com.evcharging.domain;

public class ChargingSession {
    private final String clientId;
    private final String chargerId;
    private final double lockedPricePerKwh;
    private int consumedKwh;
    private boolean active;

    public ChargingSession(String clientId, String chargerId, double lockedPricePerKwh) {
        this.clientId = clientId;
        this.chargerId = chargerId;
        this.lockedPricePerKwh = lockedPricePerKwh;
        this.consumedKwh = 0;
        this.active = true;
    }

    public String getClientId() {
        return clientId;
    }

    public String getChargerId() {
        return chargerId;
    }

    public double getLockedPricePerKwh() {
        return lockedPricePerKwh;
    }

    public int getConsumedKwh() {
        return consumedKwh;
    }

    public void addKwh(int kwh) {
        this.consumedKwh += kwh;
    }

    public boolean isActive() {
        return active;
    }

    public void stop() {
        this.active = false;
    }

    public double calculateAmount() {
        return lockedPricePerKwh * consumedKwh;
    }
}

