package com.example.gcj.controller;

import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.service.BookingExpertFeedbackQuestionService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-expert-feedback-question")
@RequiredArgsConstructor
public class BookingExpertFeedbackQuestionController {
    private final BookingExpertFeedbackQuestionService bookingExpertFeedbackQuestionService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<BookingExpertFeedbackQuestionListResponseDTO>> get(
    ) {
        List<BookingExpertFeedbackQuestionListResponseDTO> responseDTOS = bookingExpertFeedbackQuestionService.get();
        return Response.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BookingExpertFeedbackQuestionResponseDTO> getById(
            @PathVariable long id
    ) {
        BookingExpertFeedbackQuestionResponseDTO responseDTO = bookingExpertFeedbackQuestionService.getById(id);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Operation(description = "finish")
    @Secured(Role.EXPERT)
    public Response<String> create(
            @RequestBody CreateBookingExpertFeedbackQuestionRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        bookingExpertFeedbackQuestionService.create(request, expertId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateBookingExpertFeedbackQuestionRequestDTO request
    ) {
        bookingExpertFeedbackQuestionService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        bookingExpertFeedbackQuestionService.delete(id);
        return Response.ok(null);
    }
}
