package com.example.gcj.Service_Layer.dto.booking_report;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateBookingReportRequestDTO {
    private long bookingId;
    private String content;
    private String evidence;

    private List<Long> reportSuggestIds;
}
