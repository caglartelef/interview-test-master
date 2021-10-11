package com.travix.medusa.busyflights.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusyFlightsRequest {
    @Length(max = 3, min = 3, message = "Origin must have 3 characters")
    private String origin;

    @Length(max = 3, min = 3, message = "Destination must have 3 characters")
    private String destination;

    @NotNull(message = "DepartureDate cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;

    @NotNull(message = "ReturnDate cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;

    @Max(value = 4, message = "numberOfPassengers cannot be greater than 4")
    private int numberOfPassengers;
}
