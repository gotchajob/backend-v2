package com.example.gcj.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetVnPayUrlResponseDTO {
    private String paymentURL;
}
