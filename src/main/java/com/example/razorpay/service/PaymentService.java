package com.example.razorpay.service;

import com.example.razorpay.dto.request.CreateOrderRequestDto;
import com.example.razorpay.dto.request.VerifyPaymentRequestDto;
import com.example.razorpay.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createOrder(CreateOrderRequestDto dto);

    String verifyPayment(VerifyPaymentRequestDto dto);
}
