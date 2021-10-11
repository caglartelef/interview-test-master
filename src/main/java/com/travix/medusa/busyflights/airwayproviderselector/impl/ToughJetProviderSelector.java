package com.travix.medusa.busyflights.airwayproviderselector.impl;

import com.travix.medusa.busyflights.airwayproviderselector.FlightSupplierService;
import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.toughjet.ToughJetFeignClient;
import com.travix.medusa.busyflights.feign.toughjet.request.ToughJetRequest;
import com.travix.medusa.busyflights.feign.toughjet.response.ToughJetResponse;
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
public class ToughJetProviderSelector implements FlightSupplierService {
    private final ToughJetFeignClient toughJetFeignClient;
    private final ConversionService conversionService;

    /**
     * This method takes BusyFlightsRequest and converts the ToughJetRequest.
     * Calls the busy flights from Tough Jet supplier with feign client.
     * If the service reached the Tough Jet' service gets the flights. After that ToughJetResponse converts to BusyFlightsResponse.
     * If Tough Jet' service not available, the circuit breaker run and fallback scenario start.
     *
     * @param request BusyFlightsRequest
     * @return List<BusyFlightsResponse>
     */
    @Override
    public List<BusyFlightsResponse> getBusyFlights(BusyFlightsRequest request) {
        log.debug("[{}][getBusyFlights] -> request: {}", this.getClass().getSimpleName(), request);
        List<ToughJetResponse> busyFlights = toughJetFeignClient.getBusyFlights(conversionService.convert(request, ToughJetRequest.class));
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
        return FlightSupplier.TOUGH_JET;
    }

}
