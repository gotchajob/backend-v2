package com.example.gcj.Service_Layer.dto.booking_report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingReportStaffNoteRequestDTO {
    private String note;
}
