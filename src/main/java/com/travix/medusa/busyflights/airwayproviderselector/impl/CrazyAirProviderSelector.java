package com.travix.medusa.busyflights.airwayproviderselector.impl;

import com.travix.medusa.busyflights.airwayproviderselector.FlightSupplierService;
import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.crazyair.CrazyAirFeignClient;
import com.travix.medusa.busyflights.feign.crazyair.request.CrazyAirRequest;
import com.travix.medusa.busyflights.feign.crazyair.response.CrazyAirResponse;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrazyAirProviderSelector implements FlightSupplierService {
    private final CrazyAirFeignClient crazyAirFeignClient;
    private final ConversionService conversionService;

    /**
     * This method takes BusyFlightsRequest and converts the CrazyAirRequest.
     * Calls the busy flights from Crazy Air supplier with feign client.
     * If the service reached the Crazy Air' service gets the flights. After that CrazyAirResponse converts to BusyFlightsResponse.
     * If Crazy Air' service not available, the circuit breaker run and fallback scenario start.
     *
     * @param request BusyFlightsRequest
     * @return List<BusyFlightsResponse>
     */
    @Override
    public List<BusyFlightsResponse> getBusyFlights(BusyFlightsRequest request) {
        log.debug("[{}][getBusyFlights] -> request: {}", this.getClass().getSimpleName(), request);
        List<CrazyAirResponse> busyFlights = crazyAirFeignClient.getBusyFlights(conversionService.convert(request, CrazyAirRequest.class));
        if (busyFlights == null) {
            return null;
        }
        log.debug("[{}][getBusyFlights] -> response: {}", this.getClass().getSimpleName(), busyFlights);
        return busyFlights.stream()
                .map(arg -> conversionService.convert(arg, BusyFlightsResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * This method returns Flight supplier.
     *
     * @return FlightSupplier
     */
    @Override
    public FlightSupplier getProvider() {
        return FlightSupplier.CRAZY_AIR;
    }
}
