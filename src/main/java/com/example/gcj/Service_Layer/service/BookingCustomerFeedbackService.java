package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_customer_feedback.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface BookingCustomerFeedbackService {
    boolean create(long customerId, CreateBookingCustomerFeedbackRequestDTO request);

    BookingCustomerFeedbackResponseDTO getById(long id);
    BookingCustomerFeedbackResponseDTO getByBookingId(long bookingId);

    List<BookingCustomerFeedbackListResponseDTO> get();
    boolean delete(long id, long customerId);

    List<BookingCustomerFeedbackTotalRatingResponseDTO> totalRatingByExpert(long expertId);

    PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO> getListByExpert(int pageNumber, int pageSize, String sortBy, Long expertId);
}
