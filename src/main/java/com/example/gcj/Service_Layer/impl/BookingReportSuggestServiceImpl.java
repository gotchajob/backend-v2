package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.repository.BookingReportSuggestRepository;
import com.example.gcj.Service_Layer.service.BookingReportSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingReportSuggestServiceImpl implements BookingReportSuggestService {
    private final BookingReportSuggestRepository bookingReportSuggestRepository;

}
