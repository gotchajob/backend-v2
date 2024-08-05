package com.example.gcj.service;

import com.example.gcj.dto.booking.*;
import org.springframework.scheduling.annotation.Scheduled;

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
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    void updateToBookingInterviewing();
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    void updateToEndBooking();

    boolean cancel(long customerId, long id);
    boolean cancelByExpert(long expertId, long id);
    boolean reject(long currentExpertId, long id, RejectBookingRequestDTO request);

    List<BookingListResponseDTO> getByExpertId(long expertId);
    List<BookingListResponseDTO> getByExpertIdAndStatus(long expertId, Integer status);
    boolean endBooking(long id);

    boolean completeBooking(long id);
    @Scheduled(fixedRate = 60000)
    void autoCompleteBooking();

}
