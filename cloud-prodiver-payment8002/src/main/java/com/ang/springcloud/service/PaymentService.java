package com.ang.springcloud.service;

import com.ang.springcloud.entities.Payment;

public interface PaymentService {

    int add(Payment payment);

    Payment getPaymentById(Long id);
}
