package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;

import java.util.List;

public interface BookingCustomerFeedbackQuestionService {
    boolean delete(long id);

    boolean update(long id, UpdateBookingExpertFeedbackQuestionRequestDTO requestDTO);

    boolean create(CreateBookingExpertFeedbackQuestionRequestDTO requestDTO);

    BookingCustomerFeedbackQuestionResponseDTO getById(long id);

    List<BookingCustomerFeedbackQuestionListResponseDTO> get();
}
