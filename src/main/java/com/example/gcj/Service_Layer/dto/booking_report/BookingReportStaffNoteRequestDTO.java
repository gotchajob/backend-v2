package com.example.gcj.Service_Layer.dto.booking_report;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingReportStaffNoteRequestDTO {
    @NotBlank
    private String note;
}
