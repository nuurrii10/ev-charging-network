package com.evcharging.domain;

public class Pricing {
    private double acPricePerKwh;
    private double dcPricePerKwh;

    public Pricing(double acPricePerKwh, double dcPricePerKwh) {
        this.acPricePerKwh = acPricePerKwh;
        this.dcPricePerKwh = dcPricePerKwh;
    }

    public double getAcPricePerKwh() {
        return acPricePerKwh;
    }

    public void setAcPricePerKwh(double acPricePerKwh) {
        this.acPricePerKwh = acPricePerKwh;
    }

    public double getDcPricePerKwh() {
        return dcPricePerKwh;
    }

    public void setDcPricePerKwh(double dcPricePerKwh) {
        this.dcPricePerKwh = dcPricePerKwh;
    }
}
