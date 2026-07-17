package com.example.razorpay.razorpayIntegration.service;

import com.example.razorpay.razorpayIntegration.dto.request.CreateOrderRequestDto;
import com.example.razorpay.razorpayIntegration.dto.request.VerifyPaymentRequestDto;
import com.example.razorpay.razorpayIntegration.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createOrder(CreateOrderRequestDto dto);

    String verifyPayment(VerifyPaymentRequestDto dto);
}
