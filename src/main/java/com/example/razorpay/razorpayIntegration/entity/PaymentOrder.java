package com.example.razorpay.razorpayIntegration.entity;

import com.example.razorpay.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentOrder extends BaseEntity {

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

    private Double amount;

    private String currency;

    private String status;

    private String receipt;

}
