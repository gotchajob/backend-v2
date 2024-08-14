package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;

import java.util.List;

public interface BookingReportSuggestService {
    boolean create(long reportId, List<Long> reportSuggestIds);

    List<BookingReportSuggestListResponseDTO> get(Long reportId);
}
