package com.travix.medusa.busyflights.feign.crazyair.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CrazyAirRequest {
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private int passengerCount;
}
