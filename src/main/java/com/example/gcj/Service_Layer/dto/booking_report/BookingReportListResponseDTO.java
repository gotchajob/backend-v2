package com.example.gcj.Service_Layer.dto.booking_report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingReportListResponseDTO {
    private long id;
    private String customerContent;
    private String expertContent;
    private String staffNote;
    private int status;
    private long bookingId;
}
