package com.travix.medusa.busyflights.feign.toughjet;

import com.travix.medusa.busyflights.feign.toughjet.request.ToughJetRequest;
import com.travix.medusa.busyflights.feign.toughjet.response.ToughJetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * fallback -> If Tough Jet' service is not available, the ToughJetFeignClientFallback runs
 */
@FeignClient(name = "${feign-clients.tough-jet.name}", url = "${feign-clients.tough-jet.base-url}", fallback = ToughJetFeignClientFallback.class)
public interface ToughJetFeignClient {

    @PostMapping(value = "/api/1.0/tough-jet/get")
    List<ToughJetResponse> getBusyFlights(@RequestBody ToughJetRequest request);
}
