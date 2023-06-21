package com.example.debt_be.controller;

import com.example.debt_be.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;

    @GetMapping("/sms")
    public String sms(){
        return smsService.get();
    }
}
