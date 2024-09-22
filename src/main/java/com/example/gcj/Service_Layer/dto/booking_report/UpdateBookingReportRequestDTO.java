package com.example.gcj.Service_Layer.dto.booking_report;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingReportRequestDTO {
    @NotBlank
    private String content;
}
