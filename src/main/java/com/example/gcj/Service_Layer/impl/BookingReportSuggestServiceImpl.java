package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingReportSuggest;
import com.example.gcj.Repository_Layer.model.ReportSuggest;
import com.example.gcj.Repository_Layer.repository.BookingReportSuggestRepository;
import com.example.gcj.Repository_Layer.repository.ReportSuggestRepository;
import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;
import com.example.gcj.Service_Layer.mapper.BookingReportSuggestMapper;
import com.example.gcj.Service_Layer.service.BookingReportSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingReportSuggestServiceImpl implements BookingReportSuggestService {
    private final BookingReportSuggestRepository bookingReportSuggestRepository;
    private final ReportSuggestRepository reportSuggestRepository;

    @Override
    public boolean create(long reportId, List<Long> reportSuggestIds) {
        if (reportSuggestIds == null || reportSuggestIds.isEmpty()) {
            return false;
        }

        for (Long reportSuggestId : reportSuggestIds) {
            if (!reportSuggestRepository.existsById(reportSuggestId)) {
                continue;
            }

            if (bookingReportSuggestRepository.existsByReportIdAndSuggestId(reportId, reportSuggestId)) {
                continue;
            }

            BookingReportSuggest build = BookingReportSuggest
                    .builder()
                    .suggest(new ReportSuggest(reportSuggestId))
                    .reportId(reportId)
                    .build();
            bookingReportSuggestRepository.save(build);
        }

        return true;
    }

    @Override
    public List<BookingReportSuggestListResponseDTO> get(Long reportId) {
        List<BookingReportSuggest> bookingReportSuggestList = reportId == null
                ? bookingReportSuggestRepository.findAll()
                : bookingReportSuggestRepository.findByReportId(reportId);

        return bookingReportSuggestList.stream().map(BookingReportSuggestMapper::toDto).toList();
    }
}
