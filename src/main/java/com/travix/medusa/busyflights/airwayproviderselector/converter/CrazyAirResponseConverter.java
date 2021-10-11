package com.travix.medusa.busyflights.airwayproviderselector.converter;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.crazyair.response.CrazyAirResponse;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class CrazyAirResponseConverter implements Converter<CrazyAirResponse, BusyFlightsResponse> {

    /**
     * This method takes CrazyAirResponse and returns BusyFlightsResponse.
     * This method converts CrazyAirResponse to BusyFlightsResponse.
     *
     * @param crazyAirResponse CrazyAirResponse
     * @return BusyFlightsResponse
     */
    @Override
    public BusyFlightsResponse convert(CrazyAirResponse crazyAirResponse) {
        log.debug("[{}][convert] -> request: {}", this.getClass().getSimpleName(), crazyAirResponse);
        BusyFlightsResponse busyFlightsResponse = BusyFlightsResponse.builder()
                .airline(crazyAirResponse.getAirline())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(crazyAirResponse.getPrice())
                .departureAirportCode(crazyAirResponse.getDepartureAirportCode())
                .destinationAirportCode(crazyAirResponse.getDestinationAirportCode())
                .departureDate(ZonedDateTime.of(crazyAirResponse.getDepartureDate(), ZoneOffset.UTC))
                .arrivalDate(ZonedDateTime.of(crazyAirResponse.getArrivalDate(), ZoneOffset.UTC))
                .build();
        log.debug("[{}][convert] -> transformed response: {}", this.getClass().getSimpleName(), busyFlightsResponse);
        return busyFlightsResponse;
    }
}
