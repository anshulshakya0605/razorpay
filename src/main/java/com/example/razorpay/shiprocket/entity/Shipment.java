package com.example.razorpay.shiprocket.entity;

import com.example.razorpay.util.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Shipment extends BaseEntity {

    private String orderId;

    private String shipmentId;

    private String awbCode;

    private String courierName;

    private String trackingUrl;

    private String shipmentStatus;

}
