package com.evcharging.domain;

import java.util.ArrayList;
import java.util.List;

public class InvoiceManager {

    private final List<Invoice> invoices = new ArrayList<>();

    public void createInvoice(String clientId, double amount) {
        invoices.add(new Invoice(clientId, amount, "UNPAID"));
    }

    public Invoice findLatestForClient(String clientId) {
        for (int i = invoices.size() - 1; i >= 0; i--) {
            if (invoices.get(i).getClientId().equals(clientId)) {
                return invoices.get(i);
            }
        }
        return null;
    }

    public void addExisting(String clientId, double amount, String status) {
        invoices.add(new Invoice(clientId, amount, status));
    }

    public void markPaid(String clientId) {
        Invoice inv = findLatestForClient(clientId);
        if (inv != null) inv.setStatus("PAID");
    }
}
