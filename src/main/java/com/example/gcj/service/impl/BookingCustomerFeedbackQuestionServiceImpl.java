package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionListResponseDTO;
import com.example.gcj.dto.booking_customer_feedback_question.BookingCustomerFeedbackQuestionResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BookingCustomerFeedbackQuestion;
import com.example.gcj.repository.BookingCustomerFeedbackQuestionRepository;
import com.example.gcj.service.BookingCustomerFeedbackQuestionService;
import com.example.gcj.util.mapper.BookingCustomerFeedbackQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCustomerFeedbackQuestionServiceImpl implements BookingCustomerFeedbackQuestionService {
    private final BookingCustomerFeedbackQuestionRepository bookingCustomerFeedbackQuestionRepository;

    @Override
    public boolean delete(long id) {
        if (!bookingCustomerFeedbackQuestionRepository.existsById(id)) {
            throw new CustomException("not found with id " + id);
        }

        bookingCustomerFeedbackQuestionRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingExpertFeedbackQuestionRequestDTO requestDTO) {
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
    public boolean create(CreateBookingExpertFeedbackQuestionRequestDTO requestDTO) {
        BookingCustomerFeedbackQuestion build = BookingCustomerFeedbackQuestion
                .builder()
                .question(requestDTO.getQuestion())
                .type(requestDTO.getType())
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