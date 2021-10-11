package com.travix.medusa.busyflights.enums;

public enum FlightSupplier {
    CRAZY_AIR("CrazyAir"),
    TOUGH_JET("ToughJet");

    private final String value;

    FlightSupplier(String  value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
