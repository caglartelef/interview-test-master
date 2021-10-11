package com.travix.medusa.busyflights.feign.crazyair;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.crazyair.request.CrazyAirRequest;
import com.travix.medusa.busyflights.feign.crazyair.response.CrazyAirResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CrazyAirFeignClientFallback implements CrazyAirFeignClient {

    /**
     * fallback -> If Crazy Air' service is not available, the CrazyAirFeignClientFallback runs.
     * We can return null or default object.
     */
    @Override
    public List<CrazyAirResponse> getBusyFlights(CrazyAirRequest request) {
        log.error("[{}][getBusyFlights] -> error: fallback is running", this.getClass().getSimpleName());
        CrazyAirResponse crazyAirResponse = CrazyAirResponse.builder()
                .airline(FlightSupplier.CRAZY_AIR.getValue())
                .price(157.899)
                .cabinClass("B")
                .departureDate(LocalDateTime.now())
                .arrivalDate(LocalDateTime.now())
                .departureAirportCode("LHR")
                .destinationAirportCode("SAW")
                .build();
        List<CrazyAirResponse> responses = new ArrayList<>();
        responses.add(crazyAirResponse);
        return responses;
        //return null;
    }
}
