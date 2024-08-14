package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_report.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface BookingReportService {
    List<BookingReportListResponseDTO> get();

    BookingReportResponseDTO getById(long id);

    boolean create(CreateBookingReportRequestDTO request);

    boolean update(long id, UpdateBookingReportRequestDTO request);

    boolean delete(long id);

    boolean reject(long id, long staffId);

    boolean approve(long id, long staffId);

    PageResponseDTO<BookingReportListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search);

    boolean notifyExpert(long id);

    boolean updateExpertEvidence(long id, ExpertUpEvidenceRequestDTO request, long expertId);

    PageResponseDTO<BookingReportForExpertResponseDTO> getForExpert(int pageNumber, int pageSize, String sortBy, long expertId);
}
