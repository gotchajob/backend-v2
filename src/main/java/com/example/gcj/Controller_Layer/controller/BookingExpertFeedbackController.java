package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback.BookingExpertFeedbackListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback.BookingExpertFeedbackResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback.CreateBookingExpertFeedbackRequestDTO;
import com.example.gcj.Service_Layer.service.BookingExpertFeedbackService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-expert-feedback")
@RequiredArgsConstructor
public class BookingExpertFeedbackController {
    private final BookingExpertFeedbackService bookingExpertFeedbackService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<BookingExpertFeedbackListResponseDTO>> get(
        @RequestParam(required = false) @Min(1) Long bookingId
    ) {
        List<BookingExpertFeedbackListResponseDTO> response = bookingExpertFeedbackService.get(bookingId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingExpertFeedbackResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        BookingExpertFeedbackResponseDTO response = bookingExpertFeedbackService.getById(id);
        return Response.ok(response);
    }

    @GetMapping("/by-booking/{bookingId}")
    @Operation(description = "finish")
    public Response<BookingExpertFeedbackResponseDTO> getByBookingId(
            @PathVariable @Min(1) long bookingId
    ) {
        BookingExpertFeedbackResponseDTO response = bookingExpertFeedbackService.getByBookingId(bookingId);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateBookingExpertFeedbackRequestDTO requestDTO
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingExpertFeedbackService.create(requestDTO, expertId);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingExpertFeedbackService.delete(id, expertId);
        return Response.ok(null);
    }
}
