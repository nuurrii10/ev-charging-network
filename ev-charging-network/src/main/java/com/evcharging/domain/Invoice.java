package com.evcharging.domain;

public class Invoice {
    private final String clientId;
    private final double amount;
    private String status; // UNPAID / PAID

    public Invoice(String clientId, double amount, String status) {
        this.clientId = clientId;
        this.amount = amount;
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public double getAmount() {
        return amount;
    }

    public String getNetworkStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
