package com.travix.medusa.busyflights.feign.toughjet.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ToughJetRequest {
    private String from;
    private String to;
    private LocalDate outboundDate;
    private LocalDate inboundDate;
    private int numberOfAdults;
}
