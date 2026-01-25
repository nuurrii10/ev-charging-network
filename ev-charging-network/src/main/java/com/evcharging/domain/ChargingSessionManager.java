package com.evcharging.domain;

public class ChargingSessionManager {

    // statisch, damit andere Step-Klassen denselben Session-State sehen könnten
    private static ChargingSession activeSession;

    public void clear() {
        activeSession = null;
    }

    public ChargingSession startSession(String clientId, Charger charger, double lockedPricePerKwh) {
        activeSession = new ChargingSession(clientId, charger.getId(), lockedPricePerKwh);
        return activeSession;
    }

    public ChargingSession getActiveSession() {
        return activeSession;
    }

    public void consume(int kwh) {
        if (activeSession != null && activeSession.isActive() && kwh >= 0) {
            activeSession.addKwh(kwh);
        }
    }

    public double stopAndGetAmount() {
        if (activeSession == null) return 0.0;
        activeSession.stop();
        return activeSession.calculateAmount();
    }
}
