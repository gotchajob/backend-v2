package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_answer.CreateBookingExpertFeedbackAnswerRequestDTO;
import com.example.gcj.model.BookingExpertFeedbackAnswer;
import com.example.gcj.model.BookingExpertFeedbackQuestion;
import com.example.gcj.repository.BookingExpertFeedbackAnswerRepository;
import com.example.gcj.service.BookingExpertFeedbackAnswerService;
import com.example.gcj.util.mapper.BookingExpertFeedbackAnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingExpertFeedbackAnswerServiceImpl implements BookingExpertFeedbackAnswerService {
    private final BookingExpertFeedbackAnswerRepository bookingExpertFeedbackAnswerRepository;

    @Override
    public boolean create(long feedbackId, List<CreateBookingExpertFeedbackAnswerRequestDTO> request) {
        List<BookingExpertFeedbackAnswer> listToCreate = new ArrayList<>();
        for (CreateBookingExpertFeedbackAnswerRequestDTO r : request) {
            //todo: check data
            BookingExpertFeedbackAnswer build = BookingExpertFeedbackAnswer
                    .builder()
                    .answer(r.getAnswer())
                    .question(BookingExpertFeedbackQuestion.builder().id(r.getQuestionId()).build())
                    .feedbackId(feedbackId)
                    .build();
            listToCreate.add(build);
        }
        bookingExpertFeedbackAnswerRepository.saveAll(listToCreate);
        return true;
    }

    @Override
    public List<BookingExpertFeedbackAnswerListResponseDTO> getByFeedbackId(long feedbackId) {
        List<BookingExpertFeedbackAnswer> list = bookingExpertFeedbackAnswerRepository.findByFeedbackId(feedbackId);
        return list.stream().map(BookingExpertFeedbackAnswerMapper::toDto).toList();
    }
}
