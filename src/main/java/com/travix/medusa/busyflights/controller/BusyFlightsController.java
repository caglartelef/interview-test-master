package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.request.BusyFlightsRequest;
import com.travix.medusa.busyflights.response.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * I used Slf4j for logging.
 * I used Lombok in order to remove the boilerplate code of getters, setters and builders.
 */
@Slf4j
@RestController
@RequestMapping("/api/1.0/busy-flights")
@RequiredArgsConstructor
public class BusyFlightsController {
    private final BusyFlightsService busyFlightsService;

    /**
     * Accept-Language parameter gets from headers. We use this header for language support.
     * BusyFlightsRequest is request params. We accept valid request params.
     */
    @ApiOperation(value = "This endpoint returns an ordered list of flights.")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<BusyFlightsResponse>> getBusyFlights(@RequestHeader(value = "Accept-Language") Language language,
                                                                    @Valid BusyFlightsRequest request) {
        log.debug("[{}][getBusyFlights] -> request: {}", this.getClass().getSimpleName(), request);
        List<BusyFlightsResponse> busyFlights = busyFlightsService.getBusyFlights(language, request);
        log.debug("[{}][getBusyFlights] -> response: {}", this.getClass().getSimpleName(), busyFlights);
        return ResponseEntity.ok(busyFlights);
    }

}
