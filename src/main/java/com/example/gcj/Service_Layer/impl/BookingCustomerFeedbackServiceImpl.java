package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Booking;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedback;
import com.example.gcj.Repository_Layer.repository.BookingCustomerFeedbackRepository;
import com.example.gcj.Repository_Layer.repository.BookingRepository;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback.BookingCustomerFeedbackListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback.BookingCustomerFeedbackResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback.CreateBookingCustomerFeedbackRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackAnswerService;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackService;
import com.example.gcj.Service_Layer.service.ExpertSkillRatingService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.mapper.BookingCustomerFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackServiceImpl implements BookingCustomerFeedbackService {
    private final BookingCustomerFeedbackRepository bookingCustomerFeedbackRepository;
    private final BookingRepository bookingRepository;
    private final BookingCustomerFeedbackAnswerService bookingCustomerFeedbackAnswerService;
    private final ExpertSkillRatingService expertSkillRatingService;

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
        if (request.getAnswers() != null) {
            bookingCustomerFeedbackAnswerService.create(save.getBookingId(), request.getAnswers());
        }

        if (request.getSkillRatings() != null) {
            expertSkillRatingService.create(request.getSkillRatings(), build.getId());
        }

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
    public BookingCustomerFeedbackResponseDTO getByBookingId(long bookingId) {
        List<BookingCustomerFeedback> list = bookingCustomerFeedbackRepository.findByBookingId(bookingId);
        if (list == null || list.isEmpty()) {
            throw new CustomException("not found booking customer feedback");
        }
        BookingCustomerFeedback bookingCustomerFeedback = list.get(0);
        List<BookingCustomerFeedbackAnswerListResponseDTO> answerList = bookingCustomerFeedbackAnswerService.get(bookingCustomerFeedback.getId());
        return BookingCustomerFeedbackResponseDTO
                .builder()
                .id(bookingCustomerFeedback.getId())
                .bookingId(bookingCustomerFeedback.getBookingId())
                .rating(bookingCustomerFeedback.getRating())
                .comment(bookingCustomerFeedback.getComment())
                .answerList(answerList)
                .createdAt(bookingCustomerFeedback.getCreatedAt())
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
