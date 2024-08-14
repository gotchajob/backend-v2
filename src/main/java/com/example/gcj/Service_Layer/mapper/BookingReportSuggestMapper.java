package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.BookingReportSuggest;
import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;

public class BookingReportSuggestMapper {
    public static BookingReportSuggestListResponseDTO toDto(BookingReportSuggest bookingReportSuggest) {
        if (bookingReportSuggest == null || bookingReportSuggest.getSuggest() == null) {
            return null;
        }

        return BookingReportSuggestListResponseDTO
                .builder()
                .id(bookingReportSuggest.getId())
                .reportSuggestId(bookingReportSuggest.getSuggest().getId())
                .reportSuggest(bookingReportSuggest.getSuggest().getReport())
                .build();
    }
}
