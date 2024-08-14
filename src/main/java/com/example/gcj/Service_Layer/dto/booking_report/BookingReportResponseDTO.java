package com.example.gcj.Service_Layer.dto.booking_report;

import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class BookingReportResponseDTO {
    private long id;
    private String customerContent;
    private String customerEvidence;
    private String expertContent;
    private String expertEvidence;
    private String staffNote;
    private long processingBy;
    private int status;
    private long bookingId;
    private Date createdAt;
    private Date updatedAt;

    private List<BookingReportSuggestListResponseDTO> bookingReportSuggest;
}
