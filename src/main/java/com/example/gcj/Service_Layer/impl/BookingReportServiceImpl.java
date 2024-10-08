package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Booking;
import com.example.gcj.Repository_Layer.model.BookingReport;
import com.example.gcj.Repository_Layer.repository.BookingReportRepository;
import com.example.gcj.Repository_Layer.repository.BookingRepository;
import com.example.gcj.Repository_Layer.repository.SearchRepository;
import com.example.gcj.Service_Layer.dto.booking_report.*;
import com.example.gcj.Service_Layer.dto.booking_report_suggest.BookingReportSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.BookingReportMapper;
import com.example.gcj.Service_Layer.service.*;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Util;
import com.example.gcj.Shared.util.status.BookingReportStatus;
import com.example.gcj.Shared.util.status.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingReportServiceImpl implements BookingReportService {
    private final BookingReportRepository bookingReportRepository;
    private final BookingReportSuggestService bookingReportSuggestService;
    private final BookingRepository bookingRepository;
    private final SearchRepository searchRepository;
    private final ExpertService expertService;
    private final PolicyService policyService;
    private final AccountService accountService;

    @Override
    public List<BookingReportListResponseDTO> get() {
        List<BookingReport> all = bookingReportRepository.findAll();

        return all.stream().map(BookingReportMapper::toDto).toList();
    }

    @Override
    public BookingReportResponseDTO getById(long id) {
        BookingReport bookingReport = get(id);

        List<BookingReportSuggestListResponseDTO> bookingReportSuggestList = bookingReportSuggestService.get(bookingReport.getId());

        return BookingReportResponseDTO
                .builder()
                .id(bookingReport.getId())
                .customerContent(bookingReport.getCustomerContent())
                .customerEvidence(bookingReport.getCustomerEvidence())
                .expertContent(bookingReport.getExpertContent())
                .expertEvidence(bookingReport.getExpertEvidence())
                .staffNote(bookingReport.getStaffNote())
                .processingBy(bookingReport.getProcessingBy())
                .status(bookingReport.getStatus())
                .bookingId(bookingReport.getBookingId())
                .createdAt(bookingReport.getCreatedAt())
                .updatedAt(bookingReport.getUpdatedAt())
                .bookingReportSuggest(bookingReportSuggestList)
                .build();
    }

    @Override
    public boolean create(CreateBookingReportRequestDTO request, long customerId) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        checkIdAndUpdateBooking(request.getBookingId(), customerId);
        if (bookingReportRepository.existsByBookingIdAndStatus(request.getBookingId(), 1)) {
            throw new CustomException("bạn đã báo cáo lịch hẹn");
        }

        BookingReport build = BookingReport
                .builder()
                .customerContent(request.getContent())
                .customerEvidence(request.getEvidence())
                .bookingId(request.getBookingId())
                .status(BookingReportStatus.PROCESSING)
                .build();
        BookingReport save = bookingReportRepository.save(build);

        bookingReportSuggestService.create(save.getId(), request.getReportSuggestIds());

        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingReportRequestDTO request) {
        BookingReport bookingReport = get(id);

        bookingReport.setCustomerContent(request.getContent());
        bookingReportRepository.save(bookingReport);

        return true;
    }

    @Override
    public boolean delete(long id) {
        BookingReport bookingReport = get(id);

        bookingReport.setStatus(0);
        bookingReportRepository.save(bookingReport);

        return true;
    }

    @Override
    public boolean reject(long id, long staffId, BookingReportStaffNoteRequestDTO request) {
        BookingReport bookingReport = get(id);
        if (bookingReport.getStatus() != BookingReportStatus.PROCESSING
                && bookingReport.getStatus() != BookingReportStatus.EXPERT_PROCESSING
                && bookingReport.getStatus() != BookingReportStatus.STAFF_PROCESSING) {
            throw new CustomException("current status is not staff processing");
        }

        bookingReport.setStatus(BookingReportStatus.REJECT);
        bookingReport.setStaffNote(request.getNote());

        bookingReportRepository.save(bookingReport);

        return true;
    }

    @Transactional
    @Override
    public boolean approve(long id, long staffId, BookingReportStaffNoteRequestDTO request) {
        BookingReport bookingReport = get(id);
        if (bookingReport.getStatus() != BookingReportStatus.PROCESSING
                && bookingReport.getStatus() != BookingReportStatus.EXPERT_PROCESSING
                && bookingReport.getStatus() != BookingReportStatus.STAFF_PROCESSING) {
            throw new CustomException("current status is not staff processing");
        }

        bookingReport.setStatus(BookingReportStatus.APPROVE);
        bookingReport.setStaffNote(request.getNote());
        bookingReportRepository.save(bookingReport);

        int expertPointWhenReportExpert = policyService.getByKey(PolicyKey.EXPERT_POINT_WHEN_REPORT_EXPERT, Integer.class);
        Booking booking = bookingRepository.findById(bookingReport.getBookingId());
        if (booking == null) {
            throw new CustomException("not found booking");
        }
        if (booking.getStatus() == BookingStatus.COMPLETE) {
            throw new CustomException("cannot approve when booking complete");
        }

        booking.setStatus(BookingStatus.REPORTED);
        bookingRepository.save(booking);
        expertService.updateExpertPoint(booking.getExpertId(), -expertPointWhenReportExpert);

        accountService.refundWhenReport(booking.getCustomerId(), booking.getId());

        return true;
    }

    @Override
    public PageResponseDTO<BookingReportListResponseDTO> get(int pageNumber, int pageSize, String sortBy, String[] search) {
        Page<BookingReport> bookingReportPage = searchRepository.getEntitiesPage(BookingReport.class, pageNumber, pageSize, sortBy, search);

        List<BookingReportListResponseDTO> reportList = bookingReportPage.map(BookingReportMapper::toDto).toList();
        for (BookingReportListResponseDTO report : reportList) {
            report.setReportSuggest(bookingReportSuggestService.get(report.getId()));
        }

        return new PageResponseDTO<>(reportList, bookingReportPage.getTotalPages());
    }

    @Override
    public boolean notifyExpert(long id, BookingReportNotifyExpertRequestDTO request) {
        BookingReport bookingReport = get(id);
        bookingReport.setStatus(BookingReportStatus.EXPERT_PROCESSING);
        bookingReport.setStaffNote(request.getNote());
        save(bookingReport);

        //todo: notify or send mail
        return true;
    }

    @Override
    public boolean updateExpertEvidence(long id, ExpertUpEvidenceRequestDTO request, long expertId) {
        BookingReport bookingReport = get(id);

        bookingReport.setExpertEvidence(request.getEvidence());
        bookingReport.setExpertContent(request.getContent());
        bookingReport.setStatus(BookingReportStatus.STAFF_PROCESSING);
        save(bookingReport);

        return true;
    }

    @Override
    public PageResponseDTO<BookingReportForExpertResponseDTO> getForExpert(int pageNumber, int pageSize, String sortBy, long expertId) {
        Pageable pageable = Util.pageableConvert(pageNumber, pageSize, sortBy);
        Page<BookingReport> bookingReportList = bookingReportRepository.findByExpert(expertId, BookingReportStatus.EXPERT_PROCESSING, pageable);
        List<BookingReportForExpertResponseDTO> responseList = bookingReportList.map(BookingReportMapper::toExpertDto).toList();
        for (BookingReportForExpertResponseDTO responseDTO : responseList) {
            responseDTO.setBookingReportSuggest(bookingReportSuggestService.get(responseDTO.getId()));
        }

        return new PageResponseDTO<>(responseList, bookingReportList.getTotalPages());
    }

    @Override
    public PageResponseDTO<BookingReportListResponseDTO> getForCustomer(int pageNumber, int pageSize, String sortBy, long customerId) {
        Pageable pageable = Util.pageableConvert(pageNumber, pageSize, sortBy);
        Page<BookingReport> bookingReports = bookingReportRepository.findByCustomer(customerId, pageable);
        List<BookingReportListResponseDTO> reportListResponseDTOS = bookingReports.map(BookingReportMapper::toDto).toList();
        for (BookingReportListResponseDTO reportListResponseDTO : reportListResponseDTOS) {
            reportListResponseDTO.setReportSuggest(bookingReportSuggestService.get(reportListResponseDTO.getId()));
        }

        return new PageResponseDTO<>(reportListResponseDTOS, bookingReports.getTotalPages());
    }

    private BookingReport get(long id) {
        BookingReport bookingReport = bookingReportRepository.findById(id);
        if (bookingReport == null) {
            throw new CustomException("not found booking report with id " + id);
        }

        return bookingReport;
    }

    private boolean checkIdAndUpdateBooking(long bookingId, long customerId) {
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new CustomException("not found booking with id " + bookingId);
        }

        if (booking.getStatus() != BookingStatus.WAIT_TO_FEEDBACK) {
            throw new CustomException("current booking status is not wait to feedback");
        }

        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer is not same with customer in booking");
        }

        long minusAddToBookingWhenReport = policyService.getByKey(PolicyKey.PLUS_MINUS_REPORT, Long.class);
        booking.setExpireCompleteDate(booking.getExpireCompleteDate().plusMinutes(minusAddToBookingWhenReport));
        bookingRepository.save(booking);

        return true;
    }
    private BookingReport save(BookingReport bookingReport) {
        if (bookingReport == null) {
            return null;
        }

        return bookingReportRepository.save(bookingReport);
    }
}
