package com.example.razorpay.razorpayIntegration.service.impl;

import com.example.razorpay.config.RazorpayConfig;
import com.example.razorpay.razorpayIntegration.dto.request.CreateOrderRequestDto;
import com.example.razorpay.razorpayIntegration.dto.request.VerifyPaymentRequestDto;
import com.example.razorpay.razorpayIntegration.dto.response.PaymentResponseDto;
import com.example.razorpay.razorpayIntegration.entity.PaymentOrder;
import com.example.razorpay.exception.PaymentException;
import com.example.razorpay.razorpayIntegration.repository.PaymentRepository;
import com.example.razorpay.razorpayIntegration.service.PaymentService;
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

            RazorpayClient razorpayClient =
                    new RazorpayClient(razorpayConfig.getKey(), razorpayConfig.getSecret());

            JSONObject options = new JSONObject();

            options.put("amount", dto.getAmount() * 100);
            options.put("currency", dto.getCurrency());
            options.put("receipt", UUID.randomUUID().toString());

            Order order = razorpayClient.orders.create(options);

            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .razorpayOrderId(order.get("id").toString())
                    .amount(dto.getAmount())
                    .currency(dto.getCurrency())
                    .status("CREATED")
                    .receipt(order.get("receipt").toString())
                    .build();

            paymentRepository.save(paymentOrder);

            PaymentResponseDto responseDto = new PaymentResponseDto();
            responseDto.setOrderId(order.get("id").toString());
            responseDto.setAmount(dto.getAmount());
            responseDto.setKey(razorpayConfig.getKey());

            return responseDto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException("Failed to create order : " + e.getMessage());
        }
    }

    @Override
    public String verifyPayment(VerifyPaymentRequestDto dto) {

        try {

          //  String orderId = dto.getRazorpayOrderId().trim();
           // String paymentId = dto.getRazorpayPaymentId().trim();
           // String signature = dto.getRazorpaySignature().trim();
            String orderId = dto.getRazorpayOrderId();
            String paymentId = dto.getRazorpayPaymentId();
            String signature = dto.getRazorpaySignature();

            System.out.println("OrderId: " + orderId);
            System.out.println("PaymentId: " + paymentId);
            System.out.println("Signature: " + signature);

            if (orderId == null || paymentId == null || signature == null) {
                throw new PaymentException("Payment details missing");
            }

            JSONObject attributes = new JSONObject();

            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id", paymentId);
            attributes.put("razorpay_signature", signature);

            boolean isValid = Utils.verifyPaymentSignature(
                    attributes,
                    razorpayConfig.getSecret()
            );

            if (!isValid) {
                throw new PaymentException("Invalid payment signature");
            }

            PaymentOrder paymentOrder = paymentRepository
                    .findByRazorpayOrderId(orderId)
                    .orElseThrow(() -> new PaymentException("Order not found"));

            paymentOrder.setRazorpayPaymentId(paymentId);
            paymentOrder.setRazorpaySignature(signature);
            paymentOrder.setStatus("PAID");

            paymentRepository.save(paymentOrder);

            return "Payment verified successfully";

        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException("Payment verification failed : " + e.getMessage());
        }
    }
}