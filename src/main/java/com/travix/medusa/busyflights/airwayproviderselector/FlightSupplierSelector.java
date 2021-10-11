package com.travix.medusa.busyflights.airwayproviderselector;

import com.travix.medusa.busyflights.enums.FlightSupplier;

public interface FlightSupplierSelector {

    /**
     * Returns related  Flight Supplier.
     *
     * @return
     */
    FlightSupplier getProvider();
}
