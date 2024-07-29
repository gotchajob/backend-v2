package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_expert_feedback.BookingExpertFeedbackListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback.BookingExpertFeedbackResponseDTO;
import com.example.gcj.dto.booking_expert_feedback.CreateBookingExpertFeedbackRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BookingExpertFeedback;
import com.example.gcj.repository.BookingExpertFeedbackRepository;
import com.example.gcj.service.BookingExpertFeedbackAnswerService;
import com.example.gcj.service.BookingExpertFeedbackService;
import com.example.gcj.util.mapper.BookingExpertFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingExpertFeedbackServiceIml implements BookingExpertFeedbackService {
    private final BookingExpertFeedbackRepository bookingExpertFeedbackRepository;
    private final BookingExpertFeedbackAnswerService bookingExpertFeedbackAnswerService;

    @Override
    public List<BookingExpertFeedbackListResponseDTO> get(Long bookingId) {
        List<BookingExpertFeedback> bookingExpertFeedbacks = bookingId == null
                ? bookingExpertFeedbackRepository.findAll()
                : bookingExpertFeedbackRepository.getByBookingId(bookingId);

        return bookingExpertFeedbacks.stream().map(BookingExpertFeedbackMapper::toDto).toList();
    }

    @Override
    public BookingExpertFeedbackResponseDTO getById(long id) {
        BookingExpertFeedback feedback = bookingExpertFeedbackRepository.findById(id);
        if (feedback == null) {
            throw new CustomException("not found booking expert feedback with id " + id);
        }

        List<BookingExpertFeedbackAnswerListResponseDTO> answerList = bookingExpertFeedbackAnswerService.getByFeedbackId(feedback.getId());
        return BookingExpertFeedbackResponseDTO
                .builder()
                .id(feedback.getId())
                .bookingId(feedback.getBookingId())
                .comment(feedback.getComment())
                .answer(answerList)
                .build();
    }

    @Override
    public boolean create(CreateBookingExpertFeedbackRequestDTO requestDTO, long expertId) {

        BookingExpertFeedback build = BookingExpertFeedback
                .builder()
                .bookingId(requestDTO.getBookingId())
                .comment(requestDTO.getComment())
                .status(1)
                .build();
        BookingExpertFeedback save = bookingExpertFeedbackRepository.save(build);
        bookingExpertFeedbackAnswerService.create(save.getId(), requestDTO.getAnswerList());

        return true;
    }

    @Override
    public boolean delete(long id) {
        BookingExpertFeedback feedback = bookingExpertFeedbackRepository.findById(id);
        if (feedback == null) {
            throw new CustomException("not found booking expert feedback with id " + id);
        }

        feedback.setStatus(0);
        bookingExpertFeedbackRepository.save(feedback);

        return true;
    }
}
