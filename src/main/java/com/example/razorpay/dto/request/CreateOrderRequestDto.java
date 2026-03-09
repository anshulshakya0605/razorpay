package com.example.razorpay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequestDto {

    @NotNull
    private Double amount;

    @NotBlank
    private String currency;

}
