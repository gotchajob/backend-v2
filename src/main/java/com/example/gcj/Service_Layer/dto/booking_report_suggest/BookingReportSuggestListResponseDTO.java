package com.example.gcj.Service_Layer.dto.booking_report_suggest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingReportSuggestListResponseDTO {
    private long id;
    private long reportSuggestId;
    private String reportSuggest;
}
