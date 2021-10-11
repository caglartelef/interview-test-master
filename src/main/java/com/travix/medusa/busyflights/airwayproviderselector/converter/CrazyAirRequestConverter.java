package com.travix.medusa.busyflights.airwayproviderselector.converter;

import com.travix.medusa.busyflights.feign.crazyair.request.CrazyAirRequest;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrazyAirRequestConverter implements Converter<BusyFlightsRequest, CrazyAirRequest> {

    /**
     * This method takes BusyFlightsRequest and returns CrazyAirRequest.
     * This method converts BusyFlightsRequest to CrazyAirRequest.
     *
     * @param request BusyFlightsRequest
     * @return CrazyAirRequest
     */
    @Override
    public CrazyAirRequest convert(BusyFlightsRequest request) {
        log.debug("[{}][convert] -> request: {}", this.getClass().getSimpleName(), request);
        CrazyAirRequest crazyAirRequest = CrazyAirRequest.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureDate(request.getDepartureDate())
                .returnDate(request.getReturnDate())
                .passengerCount(request.getNumberOfPassengers())
                .build();
        log.debug("[{}][convert] -> transformed request: {}", this.getClass().getSimpleName(), crazyAirRequest);
        return crazyAirRequest;
    }
}
