package com.example.razorpay.controller;

import com.example.razorpay.dto.request.CreateOrderRequestDto;
import com.example.razorpay.dto.request.VerifyPaymentRequestDto;
import com.example.razorpay.dto.response.PaymentResponseDto;
import com.example.razorpay.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto dto){
        return ResponseEntity.ok(paymentService.createOrder(dto));
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<String > verifyPayment(@RequestBody VerifyPaymentRequestDto dto){
        return ResponseEntity.ok(paymentService.verifyPayment(dto));
    }

}
