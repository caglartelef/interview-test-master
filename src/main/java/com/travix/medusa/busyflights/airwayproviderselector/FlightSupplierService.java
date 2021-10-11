package com.travix.medusa.busyflights.airwayproviderselector;

import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;

import java.util.List;

public interface FlightSupplierService extends FlightSupplierSelector {

    /**
     * This method takes BusyFlightsRequest and returns List<BusyFlightsResponse>.
     * All airlines should implement this interface.
     *
     * @param request BusyFlightsRequest
     * @return List<BusyFlightsResponse>
     */
    List<BusyFlightsResponse> getBusyFlights(BusyFlightsRequest request);
}
