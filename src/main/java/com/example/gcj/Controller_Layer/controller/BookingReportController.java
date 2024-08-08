package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.service.BookingReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking-report")
@RequiredArgsConstructor
public class BookingReportController {
    private final BookingReportService bookingReportService;

}
