package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_report.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.BookingReportService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("")
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
    @Operation(description = "note finish")
    public Response<PageResponseDTO<BookingReportListResponseDTO>> getForCustomer(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<BookingReportListResponseDTO> response = bookingReportService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingReportResponseDTO> getById(
            @PathVariable long id
    ) {
        BookingReportResponseDTO response = bookingReportService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateBookingReportRequestDTO request
    ) {
        bookingReportService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateBookingReportRequestDTO request
    ) {
        bookingReportService.update(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/notify-expert")
    @Secured(Role.STAFF)
    @Operation(description = "coming soon")
    public Response<String> notifyExpert(
            @PathVariable long id
    ) {
        bookingReportService.notifyExpert(id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/expert-up-evidence")
    @Secured(Role.EXPERT)
    @Operation(description = "coming soon")
    public Response<String> expertUpEvidence(
            @PathVariable long id,
            @RequestBody ExpertUpEvidenceRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingReportService.updateExpertEvidence(id, request, expertId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/approve")
    @Operation(description = "coming soon")
    public Response<String> approve(
            @PathVariable long id
    ) {
        bookingReportService.approve(id, 0);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject")
    @Operation(description = "coming soon")
    public Response<String> reject(
            @PathVariable long id
    ) {
        //todo: add staff id
        bookingReportService.reject(id, 0);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        bookingReportService.delete(id);
        return Response.ok(null);
    }
}
