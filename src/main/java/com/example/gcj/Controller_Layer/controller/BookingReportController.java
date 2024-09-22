package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_report.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.BookingReportService;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Service_Layer.service.StaffService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking-report")
@RequiredArgsConstructor
public class BookingReportController {
    private final BookingReportService bookingReportService;
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final StaffService staffService;

    @GetMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "finish. <br> 1. processing 2. expert processing 3. staff processing, 4. approve 5. reject")
    public Response<PageResponseDTO<BookingReportListResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<BookingReportListResponseDTO> response = bookingReportService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/for-expert")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<PageResponseDTO<BookingReportForExpertResponseDTO>> getForExpert(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        long expertId = expertService.getCurrentExpertId();
        PageResponseDTO<BookingReportForExpertResponseDTO> response = bookingReportService.getForExpert(pageNumber, pageSize, sortBy, expertId);
        return Response.ok(response);
    }

    @GetMapping("/for-customer")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<PageResponseDTO<BookingReportListResponseDTO>> getForCustomer(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        long customerId = customerService.getCurrentCustomerId();
        PageResponseDTO<BookingReportListResponseDTO> response = bookingReportService.getForCustomer(pageNumber, pageSize, sortBy, customerId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingReportResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        BookingReportResponseDTO response = bookingReportService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateBookingReportRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingReportService.create(request, customerId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateBookingReportRequestDTO request
    ) {
        bookingReportService.update(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/notify-expert")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> notifyExpert(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid BookingReportNotifyExpertRequestDTO request
    ) {
        bookingReportService.notifyExpert(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/expert-up-evidence")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> expertUpEvidence(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid ExpertUpEvidenceRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingReportService.updateExpertEvidence(id, request, expertId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/approve")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> approve(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid BookingReportStaffNoteRequestDTO request
    ) {
        long currentStaffId = staffService.getCurrentStaffId();
        bookingReportService.approve(id, currentStaffId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> reject(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid BookingReportStaffNoteRequestDTO request
    ) {
        long currentStaffId = staffService.getCurrentStaffId();
        bookingReportService.reject(id, currentStaffId, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        bookingReportService.delete(id);
        return Response.ok(null);
    }
}
