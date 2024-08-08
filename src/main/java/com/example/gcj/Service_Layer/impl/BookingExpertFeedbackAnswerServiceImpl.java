package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackAnswer;
import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackQuestion;
import com.example.gcj.Repository_Layer.repository.BookingExpertFeedbackAnswerRepository;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.BookingExpertFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_answer.CreateBookingExpertFeedbackAnswerRequestDTO;
import com.example.gcj.Service_Layer.service.BookingExpertFeedbackAnswerService;
import com.example.gcj.Service_Layer.mapper.BookingExpertFeedbackAnswerMapper;
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
