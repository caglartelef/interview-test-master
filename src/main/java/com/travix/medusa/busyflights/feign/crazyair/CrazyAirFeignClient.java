package com.travix.medusa.busyflights.feign.crazyair;

import com.travix.medusa.busyflights.feign.crazyair.request.CrazyAirRequest;
import com.travix.medusa.busyflights.feign.crazyair.response.CrazyAirResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * fallback -> If Crazy Air' service is not available, the CrazyAirFeignClientFallback runs
 */
@FeignClient(name = "${feign-clients.crazy-air.name}", url = "${feign-clients.crazy-air.base-url}", fallback = CrazyAirFeignClientFallback.class)
public interface CrazyAirFeignClient {

    @PostMapping(value = "/api/1.0/crazy-air/get")
    List<CrazyAirResponse> getBusyFlights(@RequestBody CrazyAirRequest request);

}
