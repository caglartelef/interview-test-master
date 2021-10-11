package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.enums.FlightSupplier;
import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BusyFlightsControllerTest {
    private String URL = "/api/1.0/busy-flights";

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BusyFlightsService service;

    private MockMvc mockMvc;
    private List<BusyFlightsResponse> busyFlights;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        BusyFlightsResponse busyFlightsResponse1 = BusyFlightsResponse.builder()
                .airline(FlightSupplier.CRAZY_AIR.getValue())
                .supplier(FlightSupplier.CRAZY_AIR.getValue())
                .fare(150.09)
                .departureAirportCode("LHR")
                .destinationAirportCode("SAW")
                .departureDate(ZonedDateTime.now())
                .arrivalDate(ZonedDateTime.now())
                .build();

        BusyFlightsResponse busyFlightsResponse2 = BusyFlightsResponse.builder()
                .airline(FlightSupplier.TOUGH_JET.getValue())
                .supplier(FlightSupplier.TOUGH_JET.getValue())
                .fare(100.34)
                .departureAirportCode("LHR")
                .destinationAirportCode("SAW")
                .departureDate(ZonedDateTime.now())
                .arrivalDate(ZonedDateTime.now())
                .build();
        busyFlights = new ArrayList<>();
        busyFlights.add(busyFlightsResponse1);
        busyFlights.add(busyFlightsResponse2);
    }

    @Test
    void it_should_successfully_when_happy_path() throws Exception {
        // given:
        Mockito.when(service.getBusyFlights(Mockito.any(Language.class), Mockito.any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        String requestParam = "?origin=LHR&destination=SAW&departureDate=2021-10-12&returnDate=2021-10-15&numberOfPassengers=4";
        ResultActions resultActions = mockMvc.perform(get(URL + "/list" + requestParam)
                .header("Accept-Language", "EN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then:
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void it_should_fail_when_origin_characters_greater_than_three_characters() throws Exception {
        // given:
        Mockito.when(service.getBusyFlights(Mockito.any(Language.class), Mockito.any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        String requestParam = "?origin=LHRR&destination=SAW&departureDate=2021-10-12&returnDate=2021-10-15&numberOfPassengers=4";
        ResultActions resultActions = mockMvc.perform(get(URL + "/list" + requestParam)
                .header("Accept-Language", "EN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then:
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void it_should_fail_when_number_of_passengers_greater_than_four() throws Exception {
        // given:
        Mockito.when(service.getBusyFlights(Mockito.any(Language.class), Mockito.any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        String requestParam = "?origin=LHR&destination=SAW&departureDate=2021-10-12&returnDate=2021-10-15&numberOfPassengers=5";
        ResultActions resultActions = mockMvc.perform(get(URL + "/list" + requestParam)
                .header("Accept-Language", "EN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then:
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void it_should_fail_when_there_is_no_accept_language_header() throws Exception {
        // given:
        Mockito.when(service.getBusyFlights(Mockito.any(Language.class), Mockito.any(BusyFlightsRequest.class))).thenReturn(busyFlights);
        // when:
        String requestParam = "?origin=LHR&destination=SAW&departureDate=2021-10-12&returnDate=2021-10-15&numberOfPassengers=4";
        ResultActions resultActions = mockMvc.perform(get(URL + "/list" + requestParam)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then:
        resultActions.andExpect(status().isBadRequest());
    }

}