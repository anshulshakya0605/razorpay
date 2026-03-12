package com.example.razorpay.shiprocket.repository;

import com.example.razorpay.shiprocket.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, String > {
}
