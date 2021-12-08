package com.ang.springcloud.controller;


import com.ang.springcloud.entities.CommonResult;
import com.ang.springcloud.entities.Payment;
import com.ang.springcloud.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@SuppressWarnings("unchecked")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/add")
    public CommonResult add(@RequestBody Payment payment){

        int add = paymentService.add(payment);
        log.info("***********插入结果"+add);

        if(add > 0){
            return new CommonResult(200,"插入成功",add);
        }else {
            return new CommonResult(404,"插入失败",null);
        }

    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);
        System.out.println("执行了查询方法端口号："+port);
        log.info("***********插入结果"+paymentById);

        if(paymentById != null){
            return new CommonResult(200,"查询成功port:"+port,paymentById);
        }else {
            return new CommonResult(404,"查询失败port"+port,null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        //在启动类中启用
        //获取所有的服务名称
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info(service);
        }

        //获取服务下的所有实例
        List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD-PAYMENT-SERVER");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return discoveryClient;
    }
}
