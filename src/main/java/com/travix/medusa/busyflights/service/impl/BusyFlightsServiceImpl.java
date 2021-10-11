package com.travix.medusa.busyflights.service.impl;

import com.travix.medusa.busyflights.airwayproviderselector.FlightSupplierService;
import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.exception.enums.MessageCodes;
import com.travix.medusa.busyflights.exception.exceptions.BusyFlightsNotFoundException;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusyFlightsServiceImpl implements BusyFlightsService {
    /**
     * All services that have implemented the FlightSupplierService interface are added to this map.
     */
    private Map<String, FlightSupplierService> flightSupplierServiceMap = Collections.emptyMap();

    /**
     * This method takes Language, BusyFlightsRequest and returns List<BusyFlightsResponse>.
     * With this method, we get the flight list from all airlines.
     * If busyFlightsResponses is empty we throw the BusyFlightsNotFoundException. We handle this exception in GlobalExceptionHandler class.
     *
     * @param language Language, request BusyFlightsRequest
     * @return List<BusyFlightsResponse>
     */
    @Override
    public List<BusyFlightsResponse> getBusyFlights(Language language, BusyFlightsRequest request) {
        log.debug("[{}][getBusyFlights] -> request: {}", this.getClass().getSimpleName(), request);
        List<BusyFlightsResponse> busyFlightsResponses = new ArrayList<>();
        for (FlightSupplierService airwayProviderSelector : flightSupplierServiceMap.values()) {
            List<BusyFlightsResponse> responses = airwayProviderSelector.getBusyFlights(request);
            if (responses != null) {
                busyFlightsResponses.addAll(responses);
            }
        }
        if (busyFlightsResponses.isEmpty()) {
            throw new BusyFlightsNotFoundException(language, MessageCodes.BUSY_FLIGHTS_NOT_FOUND_EXCEPTION,
                    "Busy flights not found exception for origin: " + request.getOrigin()
                            + " destination: " + request.getDestination()
                            + " departureDate: " + request.getDepartureDate()
                            + " returnDate: " + request.getReturnDate()
                            + " numberOfPassengers: " + request.getNumberOfPassengers());
        }

        /*
         * We sort the flights the fare.
         * */
        busyFlightsResponses.sort(Comparator.comparing(BusyFlightsResponse::getFare));
        log.debug("[{}][getBusyFlights] -> response: {}", this.getClass().getSimpleName(), busyFlightsResponses);
        return busyFlightsResponses;
    }

    /**
     * All services that have implemented the FlightSupplierService interface are added with this method.
     */
    @Autowired
    public void setAirwayProviderSelector(Collection<FlightSupplierService> airwayProviderSelectors) {
        this.flightSupplierServiceMap = airwayProviderSelectors.stream().collect(Collectors.toMap(service -> service.getProvider().getValue(), Function.identity()));
    }
}
