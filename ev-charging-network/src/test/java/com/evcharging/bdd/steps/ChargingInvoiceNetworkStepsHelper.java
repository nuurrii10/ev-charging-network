package com.evcharging.bdd.steps;

import com.evcharging.domain.*;

public class ChargingInvoiceNetworkStepsHelper {
    public static final LocationManager locationManager = new LocationManager();
    public static final ClientManager clientManager = new ClientManager();
    public static final ChargerManager chargerManager = new ChargerManager();
    public static final PricingManager pricingManager = new PricingManager();

    public static final ChargingSessionManager sessionManager = new ChargingSessionManager();
    public static final InvoiceManager invoiceManager = new InvoiceManager();

    public static String lastRejectionReason;
}
