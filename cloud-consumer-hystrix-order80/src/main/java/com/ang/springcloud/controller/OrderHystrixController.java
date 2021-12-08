package com.ang.springcloud.controller;

import com.ang.springcloud.entities.CommonResult;
import com.ang.springcloud.entities.Payment;
import com.ang.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "global_FallbackMethod")//设置全局fallback方法·
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    @HystrixCommand//没有指定fallback方法会去找类头上DefaultProperties中指定的方法
    public String Info_OK(@PathVariable("id") int id){
        System.out.println("执行了支付端");
        return paymentHystrixService.Info_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "fallBack",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    public String Info_TimeOut(@PathVariable("id") int id){

        System.out.println("执行了支付端Timeout");
        return paymentHystrixService.Info_TimeOut(id);
    }

    //下面是全局fallbcak
    private String global_FallbackMethod(){
        return "全局异常处理执行";
    }

    //兜底方法
    private String fallBack(int id){
        return "执行报错，请重试；";
    }

    //======服务熔断=======
    @HystrixCommand(fallbackMethod = "PaymentCircuitBreaker_Fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThresholds",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String PaymentCircuitBreaker(@PathVariable("id") int id){
        if (id<0){
            throw new RuntimeException("******id 不能为负数");
        }
        return UUID.randomUUID().toString()+"调用成功";
    }

    public String PaymentCircuitBreaker_Fallback(){
        return "id不能为负数=-=================";
    }
}
