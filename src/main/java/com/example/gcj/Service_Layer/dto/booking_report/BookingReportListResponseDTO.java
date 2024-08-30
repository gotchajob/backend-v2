package com.example.gcj.Service_Layer.dto.booking_report;

import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingReportListResponseDTO {
    private long id;
    private String customerContent;
    private String expertContent;
    private String staffNote;
    private int status;
    private long bookingId;
    private Date createdAt;
    private Date updatedAt;

    private List<BookingReportSuggestListResponseDTO> reportSuggest;
}
