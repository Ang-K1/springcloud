package com.ang.springcloud.service;

import com.ang.springcloud.entities.CommonResult;
import com.ang.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/ok/{id}")
    String Info_OK(@PathVariable("id") int id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String Info_TimeOut(@PathVariable("id") int id);
}
