package com.example.gcj.controller;

import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.CreateBookingCustomerFeedbackRequestDTO;
import com.example.gcj.service.BookingCustomerFeedbackService;
import com.example.gcj.service.CustomerService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("")
    @Operation(description = "")
    public Response<List<BookingCustomerFeedbackListResponseDTO>> get(
    ) {
        List<BookingCustomerFeedbackListResponseDTO> responseDTOS = bookingCustomerFeedbackService.get();
        return Response.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingCustomerFeedbackResponseDTO> getById(
            @PathVariable long id
    ) {
        BookingCustomerFeedbackResponseDTO responseDTO = bookingCustomerFeedbackService.getById(id);
        return Response.ok(responseDTO);
    }
    @GetMapping("/by-booking/{bookingId}")
    @Operation(description = "finish")
    public Response<BookingCustomerFeedbackResponseDTO> getByBookingId(
            @PathVariable long bookingId
    ) {
        BookingCustomerFeedbackResponseDTO responseDTO = bookingCustomerFeedbackService.getByBookingId(bookingId);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateBookingCustomerFeedbackRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        bookingCustomerFeedbackService.create(customerId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "not finish")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        bookingCustomerFeedbackService.delete(id);
        return Response.ok(null);
    }
}
