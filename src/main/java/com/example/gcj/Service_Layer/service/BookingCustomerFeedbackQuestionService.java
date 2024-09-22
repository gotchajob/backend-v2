package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.CreateBookingCustomerFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.UpdateBookingCustomerFeedbackQuestionRequestDTO;

import java.util.List;

public interface BookingCustomerFeedbackQuestionService {
    boolean delete(long id);

    boolean update(long id, UpdateBookingCustomerFeedbackQuestionRequestDTO requestDTO);

    boolean create(CreateBookingCustomerFeedbackQuestionRequestDTO requestDTO);

    BookingCustomerFeedbackQuestionResponseDTO getById(long id);

    List<BookingCustomerFeedbackQuestionListResponseDTO> get();
}
