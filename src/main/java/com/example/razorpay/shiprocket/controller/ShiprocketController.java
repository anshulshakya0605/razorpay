package com.example.razorpay.shiprocket.controller;

import com.example.razorpay.shiprocket.dto.request.CreateShipmentRequestDto;
import com.example.razorpay.shiprocket.service.ShiprocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor

public class ShiprocketController {

    private final ShiprocketService shiprocketService;

    @PostMapping("/create")
    private String createShipment(@RequestBody CreateShipmentRequestDto dto){
        shiprocketService.createShipment(dto);
        return "Shipment Created Successfully";
    }

}
