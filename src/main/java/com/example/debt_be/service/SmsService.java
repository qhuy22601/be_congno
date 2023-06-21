package com.example.debt_be.service;


import com.example.debt_be.entity.model.SpeedSMSAPI;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SmsService {


    String phone = "84911560635";
    String content = "test sms";
    int type = 5;

    String sender = "4cd43ac9e36a4fac";

    SpeedSMSAPI api  = new SpeedSMSAPI("oLeZWdEYQ0Z_9VMpaZJC2_cRIWtqQUOq");
    String userInfo = api.getUserInfo();
    String response = api.sendSMS(phone, content, type, sender);

    public SmsService() throws IOException {
    }

    public String get(){
        return response;
    }
}
