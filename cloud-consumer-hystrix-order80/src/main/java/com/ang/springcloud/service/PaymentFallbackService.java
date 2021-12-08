package com.ang.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String Info_OK(int id) {
        return "PaymentFallbackService fallback Info_Ok 兜底方法执行";
    }

    @Override
    public String Info_TimeOut(int id) {
        return "return “PaymentFallbackService fallback timout 兜底方法执行;";
    }
}
