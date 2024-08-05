package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.CreateBookingExpertFeedbackAnswerRequestDTO;

import java.util.List;

public interface BookingExpertFeedbackAnswerService {

    boolean create(long feedbackId, List<CreateBookingExpertFeedbackAnswerRequestDTO> request);
    List<BookingExpertFeedbackAnswerListResponseDTO> getByFeedbackId(long feedbackId);
}
