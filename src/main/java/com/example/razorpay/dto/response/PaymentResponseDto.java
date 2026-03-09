package com.example.razorpay.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDto {

    private String orderId;

    private Double amount;

    private String key;

}
