package com.example.gcj.Service_Layer.dto.booking_report;

import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingReportForExpertResponseDTO {
    private long id;
    private String customerContent;
    private String customerEvidence;
    private long bookingId;
    private Date createdAt;

    private List<BookingReportSuggestListResponseDTO> bookingReportSuggest;
}
