package com.ljl.controller;


import com.ljl.entities.CommonResult;
import com.ljl.entities.Payment;
import com.ljl.service.PaymentService;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入成功！" + result);
        if (result > 0) {
            return new CommonResult(200, "数据插入成功！服务端口为：" + serverPort + result);
        } else {
            return new CommonResult(444, "数据插入失败！服务端口为：" + serverPort, result);
        }

    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("查询成功" + paymentById.toString());
        if (paymentById != null) {
            return new CommonResult(200, "查询成功,服务端口为：" + serverPort, paymentById);
        } else {
            return new CommonResult(444, "查询失败,服务端口为：" + serverPort + id, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info(element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance element : instances) {
            log.info(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut() {
        log.info("*****paymentFeignTimeOut from port: " + serverPort);
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }



}
