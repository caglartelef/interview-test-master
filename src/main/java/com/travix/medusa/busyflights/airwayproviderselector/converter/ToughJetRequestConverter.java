package com.travix.medusa.busyflights.airwayproviderselector.converter;

import com.travix.medusa.busyflights.feign.toughjet.request.ToughJetRequest;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ToughJetRequestConverter implements Converter<BusyFlightsRequest, ToughJetRequest> {

    /**
     * This method takes BusyFlightsRequest and returns ToughJetRequest.
     * This method converts BusyFlightsRequest to ToughJetRequest.
     *
     * @param busyFlightsRequest BusyFlightsRequest
     * @return ToughJetRequest
     */
    @Override
    public ToughJetRequest convert(BusyFlightsRequest busyFlightsRequest) {
        log.debug("[{}][convert] -> request: {}", this.getClass().getSimpleName(), busyFlightsRequest);
        ToughJetRequest toughJetRequest = ToughJetRequest.builder()
                .from(busyFlightsRequest.getOrigin())
                .to(busyFlightsRequest.getDestination())
                .outboundDate(busyFlightsRequest.getDepartureDate())
                .inboundDate(busyFlightsRequest.getReturnDate())
                .numberOfAdults(busyFlightsRequest.getNumberOfPassengers())
                .build();
        log.debug("[{}][convert] -> transformed response: {}", this.getClass().getSimpleName(), toughJetRequest);
        return toughJetRequest;
    }
}
