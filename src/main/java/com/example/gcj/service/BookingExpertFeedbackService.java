package com.example.gcj.service;

import com.example.gcj.dto.booking_expert_feedback.BookingExpertFeedbackListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback.BookingExpertFeedbackResponseDTO;
import com.example.gcj.dto.booking_expert_feedback.CreateBookingExpertFeedbackRequestDTO;

import java.util.List;

public interface BookingExpertFeedbackService {

    List<BookingExpertFeedbackListResponseDTO> get(Long bookingId);
    BookingExpertFeedbackResponseDTO getById(long id);

    boolean create(CreateBookingExpertFeedbackRequestDTO requestDTO, long expertId);

    boolean delete(long id);
}
