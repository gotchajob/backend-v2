package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingCustomerFeedbackQuestion;
import com.example.gcj.Repository_Layer.repository.BookingCustomerFeedbackQuestionRepository;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.CreateBookingCustomerFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_customer_feedback_question.UpdateBookingCustomerFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.mapper.BookingCustomerFeedbackQuestionMapper;
import com.example.gcj.Service_Layer.service.BookingCustomerFeedbackQuestionService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackQuestionServiceImpl implements BookingCustomerFeedbackQuestionService {
    private final BookingCustomerFeedbackQuestionRepository bookingCustomerFeedbackQuestionRepository;

    @Override
    public boolean delete(long id) {
        BookingCustomerFeedbackQuestion bookingCustomerFeedbackQuestion = bookingCustomerFeedbackQuestionRepository.findById(id);
        if (bookingCustomerFeedbackQuestion == null) {
            throw new CustomException("not found with id " + id);
        }

        bookingCustomerFeedbackQuestion.setStatus(0);
        bookingCustomerFeedbackQuestionRepository.save(bookingCustomerFeedbackQuestion);

        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingCustomerFeedbackQuestionRequestDTO requestDTO) {
        BookingCustomerFeedbackQuestion byId = bookingCustomerFeedbackQuestionRepository.findById(id);
        if (byId == null) {
            throw new CustomException("not found with id " + id);
        }

        byId.setQuestion(requestDTO.getQuestion());
        byId.setType(requestDTO.getType());
        bookingCustomerFeedbackQuestionRepository.save(byId);

        return true;
    }

    @Override
    public boolean create(CreateBookingCustomerFeedbackQuestionRequestDTO requestDTO) {
        BookingCustomerFeedbackQuestion build = BookingCustomerFeedbackQuestion
                .builder()
                .question(requestDTO.getQuestion())
                .type(requestDTO.getType())
                .status(1)
                .build();
        bookingCustomerFeedbackQuestionRepository.save(build);

        return true;
    }

    @Override
    public BookingCustomerFeedbackQuestionResponseDTO getById(long id) {
        BookingCustomerFeedbackQuestion byId = bookingCustomerFeedbackQuestionRepository.findById(id);
        if (byId == null) {
            throw new CustomException("not found with id " + id);
        }

        return BookingCustomerFeedbackQuestionResponseDTO
                .builder()
                .id(byId.getId())
                .question(byId.getQuestion())
                .type(byId.getType())
                .build();
    }

    @Override
    public List<BookingCustomerFeedbackQuestionListResponseDTO> get() {
        List<BookingCustomerFeedbackQuestion> all = bookingCustomerFeedbackQuestionRepository.findAll();
        return all.stream().map(BookingCustomerFeedbackQuestionMapper::toDto).toList();
    }
}
