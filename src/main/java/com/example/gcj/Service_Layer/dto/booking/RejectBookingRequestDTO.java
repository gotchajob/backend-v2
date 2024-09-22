package com.example.gcj.Service_Layer.dto.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectBookingRequestDTO {
    @NotBlank
    private String reason;
}
