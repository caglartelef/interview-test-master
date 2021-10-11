package com.travix.medusa.busyflights.airwayproviderselector.impl;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.feign.crazyair.CrazyAirFeignClient;
import com.travix.medusa.busyflights.feign.crazyair.request.CrazyAirRequest;
import com.travix.medusa.busyflights.feign.crazyair.response.CrazyAirResponse;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrazyAirProviderServiceTests {

    @InjectMocks
    private CrazyAirProviderSelector service;

    @Mock
    private CrazyAirFeignClient crazyAirFeignClient;

    @Mock
    private ConversionService conversionService;

    private BusyFlightsRequest busyFlightsRequest;
    private BusyFlightsResponse busyFlightsResponse;
    private CrazyAirRequest crazyAirRequest;
    private CrazyAirResponse crazyAirResponse;

    @BeforeEach
    public void setUp() {
        busyFlightsRequest = BusyFlightsRequest.builder()
                .origin("LHR")
                .destination("SAW")
                .departureDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .numberOfPassengers(4)
                .build();

        crazyAirRequest = CrazyAirRequest.builder()
                .origin(busyFlightsRequest.getOrigin())
                .destination(busyFlightsRequest.getDestination())
                .departureDate(busyFlightsRequest.getDepartureDate())
                .returnDate(busyFlightsRequest.getReturnDate())
                .passengerCount(busyFlightsRequest.getNumberOfPassengers())
                .build();

        crazyAirResponse = CrazyAirResponse.builder()
                .airline(FlightSupplier.CRAZY_AIR.getValue())
                .price(100.09)
                .cabinClass("B")
                .departureAirportCode("LHR")
                .destinationAirportCode("SAW")
                .departureDate(LocalDateTime.now())
                .arrivalDate(LocalDateTime.now())
                .build();

        busyFlightsResponse = BusyFlightsResponse.builder()
                .airline(crazyAirResponse.getAirline())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(crazyAirResponse.getPrice())
                .departureAirportCode(crazyAirResponse.getDepartureAirportCode())
                .destinationAirportCode(crazyAirResponse.getDestinationAirportCode())
                .departureDate(ZonedDateTime.of(crazyAirResponse.getDepartureDate(), ZoneOffset.UTC))
                .arrivalDate(ZonedDateTime.of(crazyAirResponse.getArrivalDate(), ZoneOffset.UTC))
                .build();
    }

    @Test
    public void it_should_return_null_when_crazy_air_service_unreachable() {
        // given:
        when(crazyAirFeignClient.getBusyFlights(any(CrazyAirRequest.class))).thenReturn(null);
        when(conversionService.convert(busyFlightsRequest, CrazyAirRequest.class)).thenReturn(crazyAirRequest);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(busyFlightsRequest);
        // then:
        assertNull(busyFlightsResponses);
    }

    @Test
    public void it_should_return_busy_flights_when_crazy_air_service_reachable() {
        // given:
        when(crazyAirFeignClient.getBusyFlights(any(CrazyAirRequest.class))).thenReturn(Arrays.asList(crazyAirResponse));
        when(conversionService.convert(busyFlightsRequest, CrazyAirRequest.class)).thenReturn(crazyAirRequest);
        when(conversionService.convert(crazyAirResponse, BusyFlightsResponse.class)).thenReturn(busyFlightsResponse);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(busyFlightsRequest);
        // then:
        assertNotNull(busyFlightsResponses);
    }

    @Test
    public void it_should_return_crazy_air_when_provider_is_crazy_air() {
        // given:
        // when:
        FlightSupplier flightSupplier = service.getProvider();
        // then:
        MatcherAssert.assertThat(flightSupplier.getValue(), Matchers.equalTo(FlightSupplier.CRAZY_AIR.getValue()));
    }
}