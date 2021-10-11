package com.travix.medusa.busyflights.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.utils.TwoDecimalSerializer;
import com.travix.medusa.busyflights.utils.ZonedDateTimeDeserializer;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class BusyFlightsResponse {
    private String airline;
    private String supplier;
    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double fare;
    private String departureAirportCode;
    private String destinationAirportCode;
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime departureDate;
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime arrivalDate;
}
