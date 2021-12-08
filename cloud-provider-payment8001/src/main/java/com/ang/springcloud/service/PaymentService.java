package com.ang.springcloud.service;

import com.ang.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    int add(Payment payment);

    Payment getPaymentById(Long id);
}
