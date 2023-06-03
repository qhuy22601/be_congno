//package com.example.debt_be.controller;
//
//import com.example.debt_be.entity.model.ZaloMessageRequest;
//import com.example.debt_be.service.ZaloService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ZaloController {
//    private final ZaloService zaloService;
//
//    @Autowired
//    public ZaloController(ZaloService zaloService) {
//        this.zaloService = zaloService;
//    }
//
//    @PostMapping("/zalo/send-message")
//    public void sendMessage(@RequestBody ZaloMessageRequest request) {
//        String phoneNumber = request.getPhoneNumber();
//        String message = request.getMessage();
//        zaloService.sendMessage(phoneNumber, message);
//    }
//}
