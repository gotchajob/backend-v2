package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.repository.BookingReportRepository;
import com.example.gcj.Service_Layer.service.BookingReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingReportServiceImpl implements BookingReportService {
    private final BookingReportRepository bookingReportRepository;

}
