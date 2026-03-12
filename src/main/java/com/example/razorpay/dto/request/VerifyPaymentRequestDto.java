package com.example.razorpay.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyPaymentRequestDto {

    @NotBlank
    @JsonProperty("razorpayOrderId")
    private String razorpayOrderId;

    @NotBlank
    @JsonProperty("razorpayPaymentId")
    private String razorpayPaymentId;

    @NotBlank
    @JsonProperty("razorpaySignature")
    private String razorpaySignature;

}
