package com.example.gcj.controller;

import com.example.gcj.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.service.BookingCustomerFeedbackQuestionService;
import com.example.gcj.service.BookingCustomerFeedbackService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
            @PathVariable long id
    ) {
        BookingCustomerFeedbackQuestionResponseDTO response = bookingCustomerFeedbackQuestionService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Operation(description = "finish")
    public Response<String> create(
            @RequestBody CreateBookingExpertFeedbackQuestionRequestDTO requestDTO
    ) {
        bookingCustomerFeedbackQuestionService.create(requestDTO);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateBookingExpertFeedbackQuestionRequestDTO requestDTO
    ) {
        bookingCustomerFeedbackQuestionService.update(id, requestDTO);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        bookingCustomerFeedbackQuestionService.delete(id);
        return Response.ok(null);
    }
}
