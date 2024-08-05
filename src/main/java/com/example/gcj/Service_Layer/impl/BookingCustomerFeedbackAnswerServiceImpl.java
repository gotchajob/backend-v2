package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackAnswer;
import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackQuestion;
import com.example.gcj.Repository_Layer.repository.BookingCustomerFeedbackAnswerRepository;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.BookingCustomerFeedbackAnswerListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackAnswerService;
import com.example.gcj.Shared.util.mapper.BookingCustomerFeedbackAnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackAnswerServiceImpl implements BookingCustomerFeedbackAnswerService {
    private final BookingCustomerFeedbackAnswerRepository bookingCustomerFeedbackAnswerRepository;


    @Override
    public boolean create(long bookingCustomerFeedback, List<CreateBookingCustomerFeedbackAnswerRequestDTO> requestDTOS) {
        if (requestDTOS == null) {
            return false;
        }

        List<BookingCustomerFeedbackAnswer> list = new ArrayList<>();
        for (CreateBookingCustomerFeedbackAnswerRequestDTO requestDTO : requestDTOS) {
            BookingCustomerFeedbackAnswer build = BookingCustomerFeedbackAnswer
                    .builder()
                    .bookingCustomerFeedbackId(bookingCustomerFeedback)
                    .answer(requestDTO.getAnswer())
                    .question(BookingCustomerFeedbackQuestion.builder().id(requestDTO.getQuestionId()).build())
                    .build();
            list.add(build);
        }

        bookingCustomerFeedbackAnswerRepository.saveAll(list);
        return true;
    }

    @Override
    public List<BookingCustomerFeedbackAnswerListResponseDTO> get(long feedbackId) {
        List<BookingCustomerFeedbackAnswer> list = bookingCustomerFeedbackAnswerRepository.findByBookingCustomerFeedbackId(feedbackId);
        return list.stream().map(BookingCustomerFeedbackAnswerMapper::toDto).toList();
    }
}
