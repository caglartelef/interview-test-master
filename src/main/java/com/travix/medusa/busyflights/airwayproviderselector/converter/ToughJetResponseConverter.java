package com.travix.medusa.busyflights.airwayproviderselector.converter;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.toughjet.response.ToughJetResponse;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class ToughJetResponseConverter implements Converter<ToughJetResponse, BusyFlightsResponse> {

    /**
     * This method takes ToughJetResponse and returns BusyFlightsResponse.
     * This method converts ToughJetResponse to BusyFlightsResponse.
     *
     * @param toughJetResponses ToughJetResponse
     * @return BusyFlightsResponse
     */
    @Override
    public BusyFlightsResponse convert(ToughJetResponse toughJetResponses) {
        log.debug("[{}][convert] -> request: {}", this.getClass().getSimpleName(), toughJetResponses);
        BusyFlightsResponse busyFlightsResponse = BusyFlightsResponse.builder()
                .airline(toughJetResponses.getCarrier())
                .arrivalDate(ZonedDateTime.ofInstant(toughJetResponses.getInboundDateTime(), ZoneOffset.UTC))
                .departureDate(ZonedDateTime.ofInstant(toughJetResponses.getOutboundDateTime(), ZoneOffset.UTC))
                .airline(toughJetResponses.getCarrier())
                .departureAirportCode(toughJetResponses.getDepartureAirportName())
                .destinationAirportCode(toughJetResponses.getArrivalAirportName())
                .fare(calculateFare(toughJetResponses))
                .supplier(FlightSupplier.TOUGH_JET.getValue())
                .build();
        log.debug("[{}][convert] -> transformed response: {}", this.getClass().getSimpleName(), busyFlightsResponse);
        return busyFlightsResponse;
    }

    /**
     * This method takes ToughJetResponse and returns double.
     * This method calculates the flight' fare.
     *
     * @param toughJetResponse ToughJetResponse
     * @return Double
     */
    private double calculateFare(ToughJetResponse toughJetResponse) {
        double priceWithDiscount = toughJetResponse.getBasePrice() * (100 - toughJetResponse.getDiscount()) / 100;
        return priceWithDiscount + toughJetResponse.getTax();
    }
}
