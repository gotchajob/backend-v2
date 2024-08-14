package com.example.gcj.Service_Layer.mapper;

import com.example.gcj.Repository_Layer.model.BookingReport;
import com.example.gcj.Service_Layer.dto.booking_report.BookingReportForExpertResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_report.BookingReportListResponseDTO;

public class BookingReportMapper {
    public static BookingReportListResponseDTO toDto(BookingReport bookingReport) {
        if (bookingReport == null) {
            return null;
        }

        return BookingReportListResponseDTO
                .builder()
                .id(bookingReport.getBookingId())
                .customerContent(bookingReport.getCustomerContent())
                .expertContent(bookingReport.getExpertContent())
                .staffNote(bookingReport.getStaffNote())
                .status(bookingReport.getStatus())
                .bookingId(bookingReport.getBookingId())
                .build();
    }

    public static BookingReportForExpertResponseDTO toExpertDto(BookingReport bookingReport) {
        if (bookingReport == null) {
            return null;
        }

        return BookingReportForExpertResponseDTO
                .builder()
                .id(bookingReport.getBookingId())
                .customerContent(bookingReport.getCustomerContent())
                .customerEvidence(bookingReport.getCustomerEvidence())
                .createdAt(bookingReport.getCreatedAt())
                .bookingId(bookingReport.getBookingId())
                .build();
    }
}
