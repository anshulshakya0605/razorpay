package com.example.razorpay.shiprocket.service;

import com.example.razorpay.shiprocket.dto.request.CreateShipmentRequestDto;

public interface ShiprocketService {

    String generateToken();

    void createShipment(CreateShipmentRequestDto dto);

}
