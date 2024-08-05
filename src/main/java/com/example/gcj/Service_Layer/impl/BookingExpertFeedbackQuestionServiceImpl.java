package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.BookingExpertFeedbackQuestion;
import com.example.gcj.Repository_Layer.model.ExpertQuestionCategory;
import com.example.gcj.Repository_Layer.repository.BookingExpertFeedbackQuestionRepository;
import com.example.gcj.Repository_Layer.repository.ExpertQuestionCategoryRepository;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionListResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.BookingExpertFeedbackQuestionResponseDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.CreateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.dto.booking_expert_feedback_question.UpdateBookingExpertFeedbackQuestionRequestDTO;
import com.example.gcj.Service_Layer.service.BookingExpertFeedbackQuestionService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.mapper.BookingExpertFeedbackQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingExpertFeedbackQuestionServiceImpl implements BookingExpertFeedbackQuestionService {
    private final BookingExpertFeedbackQuestionRepository bookingExpertFeedbackQuestionRepository;
    private final ExpertQuestionCategoryRepository expertQuestionCategoryRepository;

    @Override
    public boolean delete(long id) {
        if (!bookingExpertFeedbackQuestionRepository.existsById(id)) {
            throw new CustomException("not found with id " + id);
        }

        bookingExpertFeedbackQuestionRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(long id, UpdateBookingExpertFeedbackQuestionRequestDTO request, long expertId) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        ExpertQuestionCategory category = expertQuestionCategoryRepository.findById(request.getCategoryId());
        if (category == null) {
            throw new CustomException("not found expert question category with id " + request.getCategoryId());
        }
        if (category.getCreatedBy() != expertId) {
            throw new CustomException("expert question category is not yours");
        }

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
        if (!expertQuestionCategoryRepository.existsById(request.getCategoryId())) {
            throw new CustomException("not found expert question category with id " + request.getCategoryId());
        }

        BookingExpertFeedbackQuestion build = BookingExpertFeedbackQuestion
                .builder()
                .question(request.getQuestion())
                .type(request.getType())
                .category(new ExpertQuestionCategory(request.getCategoryId()))
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

    @Override
    public List<BookingExpertFeedbackQuestionListResponseDTO> getByExpertId(long expertId) {
        List<BookingExpertFeedbackQuestion> list = bookingExpertFeedbackQuestionRepository.findByCreatedBy(expertId);
        return list.stream().map(BookingExpertFeedbackQuestionMapper::toDto).toList();
    }
}
