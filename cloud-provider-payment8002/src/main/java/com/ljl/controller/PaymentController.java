package com.ljl.controller;


import com.ljl.entities.CommonResult;
import com.ljl.entities.Payment;
import com.ljl.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;

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


}
