package com.travix.medusa.busyflights.feign.toughjet;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.toughjet.request.ToughJetRequest;
import com.travix.medusa.busyflights.feign.toughjet.response.ToughJetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ToughJetFeignClientFallback implements ToughJetFeignClient {

    /**
     * fallback -> If Tough Jet' service is not available, the ToughJetFeignClientFallback runs.
     * We can return null or default object.
     */
    @Override
    public List<ToughJetResponse> getBusyFlights(ToughJetRequest request) {
        log.error("[{}][getBusyFlights] error: fallback is running", this.getClass().getSimpleName());
        ToughJetResponse toughJetResponse = ToughJetResponse.builder()
                .carrier(FlightSupplier.TOUGH_JET.getValue())
                .basePrice(100)
                .tax(18)
                .discount(10)
                .departureAirportName("LHR")
                .arrivalAirportName("SAW")
                .outboundDateTime(Instant.now())
                .inboundDateTime(Instant.now())
                .build();
        List<ToughJetResponse> responses = new ArrayList<>();
        responses.add(toughJetResponse);
        return responses;
        //return null;
    }
}
