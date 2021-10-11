package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;

import java.util.List;

public interface BusyFlightsService {

    /**
     * This method takes Language, BusyFlightsRequest and returns List<BusyFlightsResponse>.
     * With this method, we get the flight list from all airlines.
     *
     * @param language Language, request BusyFlightsRequest
     * @return List<BusyFlightsResponse>
     */
    List<BusyFlightsResponse> getBusyFlights(Language language, BusyFlightsRequest request);
}
