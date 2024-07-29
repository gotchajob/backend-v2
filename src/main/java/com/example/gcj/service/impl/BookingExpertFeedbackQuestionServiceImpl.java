package com.example.gcj.service.impl;

import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionResponseDTO;
import com.example.gcj.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.BookingExpertFeedbackQuestion;
import com.example.gcj.repository.BookingExpertFeedbackQuestionRepository;
import com.example.gcj.service.BookingExpertFeedbackQuestionService;
import com.example.gcj.util.mapper.BookingExpertFeedbackQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingExpertFeedbackQuestionServiceImpl implements BookingExpertFeedbackQuestionService {
    private final BookingExpertFeedbackQuestionRepository bookingExpertFeedbackQuestionRepository;

    @Override
    public boolean delete(long id) {
        if (!bookingExpertFeedbackQuestionRepository.existsById(id)) {
            throw new CustomException("not found with id " + id);
        }

        bookingExpertFeedbackQuestionRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingExpertFeedbackQuestionRequestDTO request) {
        BookingExpertFeedbackQuestion byId = bookingExpertFeedbackQuestionRepository.findById(id);
        if (byId == null) {
            throw new CustomException("not found with id " + id);
        }

        byId.setQuestion(request.getQuestion());
        byId.setType(request.getType());
        bookingExpertFeedbackQuestionRepository.save(byId);

        return true;
    }

    @Override
    public boolean create(CreateBookingExpertFeedbackQuestionRequestDTO request, long expertId) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        BookingExpertFeedbackQuestion build = BookingExpertFeedbackQuestion
                .builder()
                .question(request.getQuestion())
                .type(request.getType())
                .categoryId(request.getCategoryId())
                .createdBy(expertId)
                .build();
        BookingExpertFeedbackQuestion save = bookingExpertFeedbackQuestionRepository.save(build);


        return true;
    }

    @Override
    public BookingExpertFeedbackQuestionResponseDTO getById(long id) {
        BookingExpertFeedbackQuestion byId = bookingExpertFeedbackQuestionRepository.findById(id);
        if (byId == null) {
            throw new CustomException("not found with id " + id);
        }

        return BookingExpertFeedbackQuestionResponseDTO
                .builder()
                .id(byId.getId())
                .question(byId.getQuestion())
                .type(byId.getType())
                .build();
    }

    @Override
    public List<BookingExpertFeedbackQuestionListResponseDTO> get() {
        List<BookingExpertFeedbackQuestion> all = bookingExpertFeedbackQuestionRepository.findAll();
        return all.stream().map(BookingExpertFeedbackQuestionMapper::toDto).toList();
    }
}
