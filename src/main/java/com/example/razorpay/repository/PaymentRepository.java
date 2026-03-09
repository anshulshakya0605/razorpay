package com.example.razorpay.repository;

import com.example.razorpay.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentOrder, String > {

    Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId);

}
