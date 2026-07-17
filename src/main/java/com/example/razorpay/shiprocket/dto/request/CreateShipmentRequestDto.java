package com.example.razorpay.shiprocket.dto.request;

import lombok.Data;

@Data

public class CreateShipmentRequestDto {

    private String orderId;

    private String costumerName;

    private String phone;

    private String address;

    private String city;

    private String pinCode;

    private double weight;

    private double price;

}
