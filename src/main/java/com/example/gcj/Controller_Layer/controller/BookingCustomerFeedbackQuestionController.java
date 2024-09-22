package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.CreateBookingCustomerFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.UpdateBookingCustomerFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackQuestionService;
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
@RequestMapping("/booking-customer-feedback-question")
@RequiredArgsConstructor
public class BookingCustomerFeedbackQuestionController {
    private final BookingCustomerFeedbackQuestionService bookingCustomerFeedbackQuestionService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<BookingCustomerFeedbackQuestionListResponseDTO>> get(

    ) {
        List<BookingCustomerFeedbackQuestionListResponseDTO> response = bookingCustomerFeedbackQuestionService.get();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingCustomerFeedbackQuestionResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        BookingCustomerFeedbackQuestionResponseDTO response = bookingCustomerFeedbackQuestionService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody @Valid CreateBookingCustomerFeedbackQuestionRequestDTO requestDTO
    ) {
        bookingCustomerFeedbackQuestionService.create(requestDTO);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateBookingCustomerFeedbackQuestionRequestDTO requestDTO
    ) {
        bookingCustomerFeedbackQuestionService.update(id, requestDTO);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        bookingCustomerFeedbackQuestionService.delete(id);
        return Response.ok(null);
    }
}
