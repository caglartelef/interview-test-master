package com.travix.medusa.busyflights.service.impl;

import com.travix.medusa.busyflights.airwayproviderselector.FlightSupplierService;
import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.exception.exceptions.BusyFlightsNotFoundException;
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

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusyFlightsServiceImplTests {

    @InjectMocks
    private BusyFlightsServiceImpl service;

    @Mock
    private FlightSupplierService airwayProviderSelector;

    private BusyFlightsRequest busyFlightsRequest;
    private List<BusyFlightsResponse> busyFlights;

    private BusyFlightsResponse busyFlightsResponse1;
    private BusyFlightsResponse busyFlightsResponse2;

    @BeforeEach
    public void setUp() {
        when(airwayProviderSelector.getProvider()).thenReturn(FlightSupplier.CRAZY_AIR);
        service.setAirwayProviderSelector(Arrays.asList(airwayProviderSelector));
        busyFlightsRequest = BusyFlightsRequest.builder()
                .origin("LHR")
                .destination("SAW")
                .departureDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .numberOfPassengers(4)
                .build();

        busyFlightsResponse1 = BusyFlightsResponse.builder()
                .airline(FlightSupplier.CRAZY_AIR.getValue())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(150.34)
                .departureAirportCode("LHR")
                .destinationAirportCode("SAW")
                .departureDate(ZonedDateTime.now())
                .arrivalDate(ZonedDateTime.now())
                .build();

        busyFlightsResponse2 = BusyFlightsResponse.builder()
                .airline(FlightSupplier.CRAZY_AIR.getValue())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(100.09)
                .departureAirportCode("SAW")
                .destinationAirportCode("LHR")
                .departureDate(ZonedDateTime.now())
                .arrivalDate(ZonedDateTime.now())
                .build();
        busyFlights = new ArrayList<>();
        busyFlights.add(busyFlightsResponse1);
        busyFlights.add(busyFlightsResponse2);
    }

    @Test
    public void it_should_throw_exception_when_busy_flight_not_found() {
        // given:
        // when:
        // then:
        assertThrows(BusyFlightsNotFoundException.class, () -> service.getBusyFlights(Language.EN, busyFlightsRequest));
    }

    @Test
    public void it_should_crazy_air_flights_when_happy_path() {
        // given:
        when(airwayProviderSelector.getBusyFlights(any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(Language.EN, busyFlightsRequest);
        // then:
        MatcherAssert.assertThat(busyFlightsResponses.get(0).getAirline(), Matchers.equalTo(busyFlights.get(0).getAirline()));
    }

    @Test
    public void it_should_sorted_flights_with_fare_flights_when_happy_path() {
        // given:
        when(airwayProviderSelector.getBusyFlights(any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        List<BusyFlightsResponse> busyFlightsResponses = service.getBusyFlights(Language.EN, busyFlightsRequest);
        // then:
        MatcherAssert.assertThat(busyFlightsResponses.get(0).getAirline(), Matchers.equalTo(busyFlightsResponse2.getAirline()));
        MatcherAssert.assertThat(busyFlightsResponses.get(1).getAirline(), Matchers.equalTo(busyFlightsResponse1.getAirline()));
    }

}