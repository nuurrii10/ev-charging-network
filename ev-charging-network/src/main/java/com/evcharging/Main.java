package com.evcharging;

import com.evcharging.domain.*;

public class Main {
    public static void main(String[] args) {

        // Managers
        LocationManager locationManager = new LocationManager();
        ClientManager clientManager = new ClientManager();
        ChargerManager chargerManager = new ChargerManager();
        PricingManager pricingManager = new PricingManager();
        ChargingSessionManager sessionManager = new ChargingSessionManager();
        InvoiceManager invoiceManager = new InvoiceManager();

        // Setup
        locationManager.addLocation("City Center", "ACTIVE");
        pricingManager.setPrices("City Center", 0.40, 0.60);

        clientManager.addClient("U1", 20);

        chargerManager.addCharger("C1", "AC", "City Center", "ONLINE");

        // Start session
        Charger charger = chargerManager.findById("C1");
        double lockedPrice = pricingManager.getPricePerKwh(charger.getLocationName(), charger.getType());

        sessionManager.startSession("U1", charger, lockedPrice);
        sessionManager.consume(10); // 10 kWh
        double amount = sessionManager.stopAndGetAmount();

        // Apply business rules
        Client client = clientManager.findById("U1");
        client.setBalance(client.getBalance() - (int) Math.round(amount));
        invoiceManager.createInvoice("U1", amount);

        // Output
        Invoice invoice = invoiceManager.findLatestForClient("U1");

        System.out.println("=== DEMO PART2 ===");
        System.out.println("Location: City Center, AC price: 0.40");
        System.out.println("Client U1 balance after charging: " + client.getBalance());
        System.out.println("Invoice amount: " + invoice.getAmount());
        System.out.println("Invoice status: " + invoice.getNetworkStatus());
    }
}
