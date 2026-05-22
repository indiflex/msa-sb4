package com.msa.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${client.user}")
public interface UserClient {
    @PatchMapping("/internal/users/addpoint")
    void addPoint(@RequestBody AddPointDTO dto);
}
