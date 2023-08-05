package com.ljl.controller;

import com.ljl.entities.CommonResult;
import com.ljl.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderController {
    @Resource
    private RestTemplate restTemplate;
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")

    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")

    public CommonResult<Payment> getEntity(@PathVariable("id") Long id) {
//        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        ResponseEntity<CommonResult> entity = restTemplate
                .getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        log.info("====" + entity.toString());
        log.info("===" + entity.getStatusCode());
        log.info("===" + entity.getHeaders());
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "失败！");
        }
    }

//    @GetMapping("/consumer/payment/Entity/create")
//    public CommonResult<Payment> createEntity(Payment payment) {
//        /* return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);*/
//        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
//
//
//    }
}
