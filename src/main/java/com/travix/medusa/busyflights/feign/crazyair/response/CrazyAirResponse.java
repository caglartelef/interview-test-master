package com.travix.medusa.busyflights.feign.crazyair.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CrazyAirResponse {
    private String airline;
    private double price;
    private String cabinClass;
    private String departureAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
}
