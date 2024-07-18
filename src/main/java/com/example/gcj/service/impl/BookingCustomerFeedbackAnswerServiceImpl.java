package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_customer_feedback_answer.CreateBookingCustomerFeedbackAnswerRequestDTO;
import com.example.gcj.model.BookingCustomerFeedbackAnswer;
import com.example.gcj.repository.BookingCustomerFeedbackAnswerRepository;
import com.example.gcj.service.BookingCustomerFeedbackAnswerService;
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
        List<BookingCustomerFeedbackAnswer> list = new ArrayList<>();
        for (CreateBookingCustomerFeedbackAnswerRequestDTO requestDTO : requestDTOS) {
            BookingCustomerFeedbackAnswer build = BookingCustomerFeedbackAnswer
                    .builder()
                    .bookingCustomerFeedbackId(bookingCustomerFeedback)
                    .answer(requestDTO.getAnswer())
                    .questionId(requestDTO.getQuestionId())
                    .build();
            list.add(build);
        }

        bookingCustomerFeedbackAnswerRepository.saveAll(list);
        return true;
    }
}
