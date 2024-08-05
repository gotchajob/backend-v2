package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;

import java.util.List;

public interface BookingCustomerFeedbackAnswerService {
    boolean create (long bookingCustomerFeedback, List<CreateBookingCustomerFeedbackAnswerRequestDTO> requestDTOS);
    List<BookingCustomerFeedbackAnswerListResponseDTO> get(long feedbackId);
}
