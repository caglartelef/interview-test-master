package com.travix.medusa.busyflights.airwayproviderselector.impl;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.toughjet.ToughJetFeignClient;
import com.travix.medusa.busyflights.feign.toughjet.request.ToughJetRequest;
import com.travix.medusa.busyflights.feign.toughjet.response.ToughJetResponse;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToughJetProviderServiceTests {

    @InjectMocks
    private ToughJetProviderSelector service;

    @Mock
    private ToughJetFeignClient toughJetFeignClient;

    @Mock
    private ConversionService conversionService;

    private BusyFlightsRequest busyFlightsRequest;
    private BusyFlightsResponse busyFlightsResponse;
    private ToughJetRequest toughJetRequest;
    private ToughJetResponse toughJetResponse;

    @BeforeEach
    public void setUp() {
        busyFlightsRequest = BusyFlightsRequest.builder()
                .origin("LHR")
                .destination("SAW")
                .departureDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .numberOfPassengers(4)
                .build();

        toughJetRequest = ToughJetRequest.builder()
                .from(busyFlightsRequest.getOrigin())
                .to(busyFlightsRequest.getDestination())
                .outboundDate(busyFlightsRequest.getDepartureDate())
                .inboundDate(busyFlightsRequest.getReturnDate())
                .numberOfAdults(busyFlightsRequest.getNumberOfPassengers())
                .build();

        toughJetResponse = ToughJetResponse.builder()
                .carrier(FlightSupplier.CRAZY_AIR.getValue())
                .basePrice(100.09)
                .tax(10)
                .discount(3)
                .departureAirportName("LHR")
                .arrivalAirportName("SAW")
                .outboundDateTime(Instant.now())
                .inboundDateTime(Instant.now())
                .build();

        busyFlightsResponse = BusyFlightsResponse.builder()
                .airline(toughJetResponse.getCarrier())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(calculateFare(toughJetResponse))
                .departureAirportCode(toughJetResponse.getDepartureAirportName())
                .destinationAirportCode(toughJetResponse.getArrivalAirportName())
                .departureDate(ZonedDateTime.ofInstant(toughJetResponse.getOutboundDateTime(), ZoneOffset.UTC))
                .arrivalDate(ZonedDateTime.ofInstant(toughJetResponse.getInboundDateTime(), ZoneOffset.UTC))
                .build();
    }

    @Test
    public void it_should_return_null_when_tough_jet_service_unreachable() {
        // given:
        when(toughJetFeignClient.getBusyFlights(any(ToughJetRequest.class))).thenReturn(null);
        when(conversionService.convert(busyFlightsRequest, ToughJetRequest.class)).thenReturn(toughJetRequest);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(busyFlightsRequest);
        // then:
        assertNull(busyFlightsResponses);
    }

    @Test
    public void it_should_return_busy_flights_when_tough_jetr_service_reachable() {
        // given:
        when(toughJetFeignClient.getBusyFlights(any(ToughJetRequest.class))).thenReturn(Arrays.asList(toughJetResponse));
        when(conversionService.convert(busyFlightsRequest, ToughJetRequest.class)).thenReturn(toughJetRequest);
        when(conversionService.convert(toughJetResponse, BusyFlightsResponse.class)).thenReturn(busyFlightsResponse);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(busyFlightsRequest);
        // then:
        assertNotNull(busyFlightsResponses);
    }

    @Test
    public void it_should_return_tough_jet_when_provider_is_tough_jet() {
        // given:
        // when:
        FlightSupplier flightSupplier = service.getProvider();
        // then:
        MatcherAssert.assertThat(flightSupplier.getValue(), Matchers.equalTo(FlightSupplier.TOUGH_JET.getValue()));
    }

    private double calculateFare(ToughJetResponse toughJetResponse) {
        double priceWithDiscount = toughJetResponse.getBasePrice() * (100 - toughJetResponse.getDiscount()) / 100;
        return priceWithDiscount + toughJetResponse.getTax();
    }

}