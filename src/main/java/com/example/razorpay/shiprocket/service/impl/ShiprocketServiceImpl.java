package com.example.razorpay.shiprocket.service.impl;

import com.example.razorpay.client.ShiprocketClient;
import com.example.razorpay.shiprocket.dto.request.CreateShipmentRequestDto;
import com.example.razorpay.shiprocket.entity.Shipment;
import com.example.razorpay.shiprocket.repository.ShipmentRepository;
import com.example.razorpay.shiprocket.service.ShiprocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ShiprocketServiceImpl implements ShiprocketService {

    private final ShiprocketClient shiprocketClient;
    private final ShipmentRepository shipmentRepository;

    @Value("${shiprocket.email}")
    private String email;

    @Value("${shiprocket.password}")
    private String password;

    @Override
    public String generateToken() {

        Map<String, String > body = new HashMap<>();

        body.put("email", email);
        body.put("password", password);

        Map response = shiprocketClient.login(body).getBody();

        return response.get("token").toString();
    }

    @Override
    public void createShipment(CreateShipmentRequestDto dto) {

        String token = generateToken();

        Map<String, Object> body = new HashMap<>();

        body.put("order_id", dto.getOrderId());
        body.put("order_date", "2026-03-12");
        body.put("pickup_location", "Home");

        body.put("billing_costumer_name", dto.getCostumerName());
        body.put("billing_phone", dto.getPhone());
        body.put("billing_address", dto.getAddress());
        body.put("billing_city", dto.getCity());
        body.put("billing_pinCode", dto.getPinCode());
        body.put("billing_state", "Delhi");
        body.put("billing_country", "India");

        body.put("payment_method", "Prepaid");
        body.put("billing_price", dto.getPrice());

        body.put("length", 10);
        body.put("breadth", 10);
        body.put("height", 10);
        body.put("weight", dto.getWeight());

        Map response = shiprocketClient.createShipment(token, body).getBody();

        Shipment shipment = new Shipment();

        shipment.setOrderId(dto.getOrderId());
        shipment.setShipmentId(response.get("shipment_id").toString());
        shipment.setShipmentStatus("CREATED");

        shipmentRepository.save(shipment);
    }
}
