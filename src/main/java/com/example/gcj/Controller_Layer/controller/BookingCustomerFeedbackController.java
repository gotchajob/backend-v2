package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackService;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-customer-feedback")
@RequiredArgsConstructor
public class BookingCustomerFeedbackController {
    private final BookingCustomerFeedbackService bookingCustomerFeedbackService;
    private final CustomerService customerService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "")
    public Response<PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) Long expertId
    ) {
        PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO> response = bookingCustomerFeedbackService.getListByExpert(pageNumber, pageSize, sortBy, expertId);
        return Response.ok(response);
    }

    @GetMapping("/total-rating")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<List<BookingCustomerFeedbackTotalRatingResponseDTO>> getTotalRating(
    ) {
        long expertId = expertService.getCurrentExpertId();
        List<BookingCustomerFeedbackTotalRatingResponseDTO> response = bookingCustomerFeedbackService.totalRatingByExpert(expertId);
        return Response.ok(response);
    }
    @GetMapping("/total-rating/{expertId}")
    @Operation(description = "")
    public Response<List<BookingCustomerFeedbackTotalRatingResponseDTO>> getTotalRatingByExpertId(
            @PathVariable @Min(1) long expertId
    ) {
        List<BookingCustomerFeedbackTotalRatingResponseDTO> response = bookingCustomerFeedbackService.totalRatingByExpert(expertId);
        return Response.ok(response);
    }

    @GetMapping("/for-expert")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO>> getRating(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy
    ) {
        long expertId = expertService.getCurrentExpertId();
        PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO> response = bookingCustomerFeedbackService.getListByExpert(pageNumber, pageSize, sortBy, expertId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingCustomerFeedbackResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        BookingCustomerFeedbackResponseDTO responseDTO = bookingCustomerFeedbackService.getById(id);
        return Response.ok(responseDTO);
    }

    @GetMapping("/by-booking/{bookingId}")
    @Operation(description = "finish")
    public Response<BookingCustomerFeedbackResponseDTO> getByBookingId(
            @PathVariable @Min(1) long bookingId
    ) {
        BookingCustomerFeedbackResponseDTO responseDTO = bookingCustomerFeedbackService.getByBookingId(bookingId);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateBookingCustomerFeedbackRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingCustomerFeedbackService.create(customerId, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingCustomerFeedbackService.delete(id, customerId);
        return Response.ok(null);
    }
}
