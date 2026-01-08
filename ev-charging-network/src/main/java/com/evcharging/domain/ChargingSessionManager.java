package com.evcharging.domain;

public class ChargingSessionManager {

    private ChargingSession activeSession;

    public ChargingSession startSession(String clientId, Charger charger, double lockedPricePerKwh) {
        activeSession = new ChargingSession(clientId, charger.getId(), lockedPricePerKwh);
        return activeSession;
    }

    public ChargingSession getActiveSession() {
        return activeSession;
    }

    public void consume(int kwh) {
        if (activeSession != null && activeSession.isActive()) {
            activeSession.addKwh(kwh);
        }
    }

    public double stopAndGetAmount() {
        if (activeSession == null) return 0.0;
        activeSession.stop();
        return activeSession.calculateAmount();
    }
}
