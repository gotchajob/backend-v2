package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.BookingRejectSuggestResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.CreateBookingRejectSuggestRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_reject_suggest.UpdateBookingRejectSuggestRequestDTO;

import java.util.List;

public interface BookingRejectSuggestService {
    boolean delete(long id);

    boolean update(long id, UpdateBookingRejectSuggestRequestDTO request);

    boolean create(CreateBookingRejectSuggestRequestDTO request);

    BookingRejectSuggestResponseDTO getById(long id);

    List<BookingRejectSuggestListResponseDTO> get(Integer type);
}
