package com.example.gcj.service;

import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.CreateBookingCustomerFeedbackRequestDTO;

import java.util.List;

public interface BookingCustomerFeedbackService {
    boolean create(long customerId, CreateBookingCustomerFeedbackRequestDTO request);

    BookingCustomerFeedbackResponseDTO getById(long id);

    List<BookingCustomerFeedbackListResponseDTO> get();
    boolean delete(long id);
}
