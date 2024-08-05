package com.example.gcj.service;

import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;

import java.util.List;

public interface BookingExpertFeedbackQuestionService {
    boolean delete(long id);

    boolean update(long id, UpdateBookingExpertFeedbackQuestionRequestDTO request, long expertId);

    boolean create(CreateBookingExpertFeedbackQuestionRequestDTO request, long expertId);

    BookingExpertFeedbackQuestionResponseDTO getById(long id);

    List<BookingExpertFeedbackQuestionListResponseDTO> get();

    List<BookingExpertFeedbackQuestionListResponseDTO> getByExpertId(long expertId);
}
