package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.BookingCustomerFeedbackResponseDTO;
import com.example.gcj.dto.booking_customer_feedback.CreateBookingCustomerFeedbackRequestDTO;
import com.example.gcj.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Booking;
import com.example.gcj.model.BookingCustomerFeedback;
import com.example.gcj.repository.BookingCustomerFeedbackRepository;
import com.example.gcj.repository.BookingRepository;
import com.example.gcj.service.BookingCustomerFeedbackAnswerService;
import com.example.gcj.service.BookingCustomerFeedbackService;
import com.example.gcj.util.mapper.BookingCustomerFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackServiceImpl implements BookingCustomerFeedbackService {
    private final BookingCustomerFeedbackRepository bookingCustomerFeedbackRepository;
    private final BookingRepository bookingRepository;
    private final BookingCustomerFeedbackAnswerService bookingCustomerFeedbackAnswerService;

    @Override
    public boolean create(long customerId, CreateBookingCustomerFeedbackRequestDTO request) {
        Booking booking = bookingRepository.findById(request.getBookingId());
        if (booking == null) {
            throw new CustomException("not found booking with id " + request.getBookingId());
        }
        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer not same with customer id in booking");
        }

        BookingCustomerFeedback build = BookingCustomerFeedback
                .builder()
                .bookingId(request.getBookingId())
                .comment(request.getComment())
                .status(1)
                .rating(request.getRating())
                .build();

        BookingCustomerFeedback save = bookingCustomerFeedbackRepository.save(build);

        bookingCustomerFeedbackAnswerService.create(save.getBookingId(), request.getAnswers());
        return true;
    }

    @Override
    public BookingCustomerFeedbackResponseDTO getById(long id) {
        BookingCustomerFeedback byId = bookingCustomerFeedbackRepository.findById(id);
        if (byId == null) {
            throw new CustomException("not found with id " + id);
        }

        List<BookingCustomerFeedbackAnswerListResponseDTO> answerList = bookingCustomerFeedbackAnswerService.get(byId.getId());
        return BookingCustomerFeedbackResponseDTO
                .builder()
                .id(byId.getId())
                .bookingId(byId.getBookingId())
                .rating(byId.getRating())
                .comment(byId.getComment())
                .answerList(answerList)
                .build();
    }

    @Override
    public List<BookingCustomerFeedbackListResponseDTO> get() {
        List<BookingCustomerFeedback> all = bookingCustomerFeedbackRepository.findAll();
        return all.stream().map(BookingCustomerFeedbackMapper::toDto).toList();
    }

    @Override
    public boolean delete(long id) {
        BookingCustomerFeedback feedback = bookingCustomerFeedbackRepository.findById(id);
        if (feedback == null) {
            throw new CustomException("not found booking customer feedback with id " + id);
        }

        feedback.setStatus(0);
        bookingCustomerFeedbackRepository.save(feedback);

        return true;
    }
}
