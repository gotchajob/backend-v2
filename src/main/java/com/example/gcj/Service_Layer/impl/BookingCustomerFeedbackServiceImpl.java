package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Booking;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedback;
import com.example.gcj.Repository_Layer.repository.BookingCustomerFeedbackRepository;
import com.example.gcj.Repository_Layer.repository.BookingRepository;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback.*;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.BookingCustomerFeedbackMapper;
import com.example.gcj.Service_Layer.service.*;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.status.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackServiceImpl implements BookingCustomerFeedbackService {
    private final BookingCustomerFeedbackRepository bookingCustomerFeedbackRepository;
    private final BookingRepository bookingRepository;
    private final BookingCustomerFeedbackAnswerService bookingCustomerFeedbackAnswerService;
    private final ExpertSkillRatingService expertSkillRatingService;
    private final ExpertService expertService;
    private final PolicyService policyService;

    @Override
    public boolean create(long customerId, CreateBookingCustomerFeedbackRequestDTO request) {
        Booking booking = bookingRepository.findById(request.getBookingId());
        if (booking == null) {
            throw new CustomException("not found booking with id " + request.getBookingId());
        }
        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer not same with customer id in booking");
        }
        if (booking.getStatus() != BookingStatus.INTERVIEWING
                && booking.getStatus() != BookingStatus.WAIT_TO_FEEDBACK
                && booking.getStatus() != BookingStatus.COMPLETE) {
            throw new CustomException("booking invalid status!");
        }

        if (bookingCustomerFeedbackRepository.existsByBookingIdAndStatus(booking.getId(), 1)) {
            throw new CustomException("you are already feedback!");
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

        if (request.getRating() == 5) {
            int pointWhenGoodFeedback = policyService.getByKey(PolicyKey.EXPERT_POINT_WHEN_HAVE_GOOD_FEEDBACK, Integer.class);
            expertService.updateExpertPoint(booking.getExpertId(), pointWhenGoodFeedback);
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
    public boolean delete(long id, long customerId) {

        BookingCustomerFeedback feedback = bookingCustomerFeedbackRepository.findById(id);
        if (feedback == null) {
            throw new CustomException("not found booking customer feedback with id " + id);
        }

        Booking booking = bookingRepository.findById(feedback.getBookingId());
        if (booking == null) {
            throw new CustomException("not found booking with id " + feedback.getBookingId());
        }

        if (booking.getCustomerId() != customerId) {
            throw new CustomException("current customer not same with customer id in booking t");
        }

        feedback.setStatus(0);
        bookingCustomerFeedbackRepository.save(feedback);

        return true;
    }

    @Override
    public List<BookingCustomerFeedbackTotalRatingResponseDTO> totalRatingByExpert(long expertId) {
        return bookingCustomerFeedbackRepository.getTotalRatingByExpert(expertId);
    }

    @Override
    public PageResponseDTO<BookingCustomerFeedbackSimpleResponseDTO> getListByExpert(int pageNumber, int pageSize, String sortBy, Long expertId) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).descending());
        Page<BookingCustomerFeedback> page = bookingCustomerFeedbackRepository.findByExpertId(expertId, pageable);
        return new PageResponseDTO<>(page.map(BookingCustomerFeedbackMapper::toDtoSimple).toList(), page.getTotalPages());
    }
}
