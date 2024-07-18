package com.example.gcj.service;

import com.example.gcj.dto.booking.*;

import java.util.List;

public interface BookingService {
    void delete(long id);

    boolean update(long id, UpdateBookingRequestDTO request);

    boolean create(long customerId, CreateBookingRequestDTO request);

    List<BookingListResponseDTO> getAll();

    List<BookingListResponseDTO> getByCurrent(long customerId);
    List<BookingListResponseDTO> getByCurrentAndStatus(long customerId, Integer status);

    BookingResponseDTO getById(long id);
    boolean updateStatus(long id, int status);
    boolean approve(long expertId, long id);
    boolean cancel(long customerId, long id);


    boolean reject(long currentExpertId, long id, RejectBookingRequestDTO request);

    List<BookingListResponseDTO> getByExpertId(long expertId);
    List<BookingListResponseDTO> getByExpertIdAndStatus(long expertId, Integer status);
}
