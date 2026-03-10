package com.example.razorpay.service.impl;

import com.example.razorpay.config.RazorpayConfig;
import com.example.razorpay.dto.request.CreateOrderRequestDto;
import com.example.razorpay.dto.request.VerifyPaymentRequestDto;
import com.example.razorpay.dto.response.PaymentResponseDto;
import com.example.razorpay.entity.PaymentOrder;
import com.example.razorpay.exception.PaymentException;
import com.example.razorpay.repository.PaymentRepository;
import com.example.razorpay.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayConfig razorpayConfig;

    @Override
    public PaymentResponseDto createOrder(CreateOrderRequestDto dto) {

        try {

            RazorpayClient razorpayClient = new RazorpayClient(
                    razorpayConfig.getKey(), razorpayConfig.getSecret()
            );

            JSONObject options = new JSONObject();

            options.put("amount", dto.getAmount() * 100);
            options.put("currency", dto.getCurrency());
            options.put("receipt", UUID.randomUUID().toString());

            Order order = razorpayClient.orders.create(options);

            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .razorpayOrderId(order.get("id"))
                    .amount(dto.getAmount())
                    .currency(dto.getCurrency())
                    .status("CREATED")
                    .receipt(order.get("receipt"))
                    .build();

            paymentRepository.save(paymentOrder);

            PaymentResponseDto responseDto = new PaymentResponseDto();

            responseDto.setOrderId(order.get("id"));
            responseDto.setAmount(dto.getAmount());
            responseDto.setKey(razorpayConfig.getKey());

            return responseDto;

        }catch (Exception e){
            throw new PaymentException("Failed to create order");
        }

    }

    @Override
    public String verifyPayment(VerifyPaymentRequestDto dto) {

        System.out.println("OrderId: " + dto.getRazorpayOrderId());
        System.out.println("PaymentId: " + dto.getRazorpayPaymentId());
        System.out.println("Signature: " + dto.getRazorpaySignature());

        try {

            if(dto.getRazorpayPaymentId()==null || dto.getRazorpaySignature()==null){
                throw new PaymentException("Payment details missing");
            }

            String payload = dto.getRazorpayOrderId() + "|" + dto.getRazorpayPaymentId();

            boolean isValid = Utils.verifySignature(
                    payload,
                    dto.getRazorpaySignature(),
                    razorpayConfig.getSecret()
            );

            if (!isValid){
                throw new PaymentException("Invalid payment signature");
            }

            PaymentOrder paymentOrder = paymentRepository.findByRazorpayOrderId(dto.getRazorpayOrderId())
                    .orElseThrow(()->new PaymentException("Order not found"));

            paymentOrder.setRazorpayPaymentId(dto.getRazorpayPaymentId());
            paymentOrder.setRazorpaySignature(dto.getRazorpaySignature());
            paymentOrder.setStatus("PAID");

            paymentRepository.save(paymentOrder);

            return "Payment verified successfully";

        }catch (Exception e){
            e.printStackTrace();
            throw new PaymentException("Payment verification failed : " + e.getMessage());
        }
    }
}
