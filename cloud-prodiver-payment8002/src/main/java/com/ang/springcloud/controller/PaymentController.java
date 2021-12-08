package com.ang.springcloud.controller;


import com.ang.springcloud.entities.CommonResult;
import com.ang.springcloud.entities.Payment;
import com.ang.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@SuppressWarnings("unchecked")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/add")
    public CommonResult add(@RequestBody Payment payment) {
        int add = paymentService.add(payment);
        log.info("***********插入结果" + add);

        if (add > 0) {
            return new CommonResult(200, "插入成功", add);
        } else {
            return new CommonResult(404, "插入失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        System.out.println("执行了查询方法,端口号：" + port);
        log.info("***********插入结果" + paymentById);

        if (paymentById != null) {
            return new CommonResult(200, "查询成功" + "port:" + port, paymentById);
        } else {
            return new CommonResult(404, "查询失败port:" + port, null);
        }
    }
}
