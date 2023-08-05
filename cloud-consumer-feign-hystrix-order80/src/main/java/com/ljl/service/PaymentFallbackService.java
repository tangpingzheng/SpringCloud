package com.ljl.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "调用失败！请稍后再试！";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return null;
    }
}
