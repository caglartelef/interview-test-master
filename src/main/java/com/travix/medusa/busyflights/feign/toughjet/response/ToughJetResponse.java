package com.travix.medusa.busyflights.feign.toughjet.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ToughJetResponse {
    private String carrier;
    private double basePrice;
    private double tax;
    private double discount;
    private String departureAirportName;
    private String arrivalAirportName;
    private Instant outboundDateTime;
    private Instant inboundDateTime;
}
