package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ExpertSkillOption;
import com.example.gcj.Repository_Layer.model.ExpertSkillRating;
import com.example.gcj.Repository_Layer.repository.ExpertSkillOptionRepository;
import com.example.gcj.Repository_Layer.repository.ExpertSkillRatingRepository;
import com.example.gcj.Repository_Layer.repository.SearchRepository;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingTotalRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.mapper.ExpertSkillRatingMapper;
import com.example.gcj.Service_Layer.service.ExpertSkillRatingService;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertSkillRatingServiceImpl implements ExpertSkillRatingService {
    private final ExpertSkillRatingRepository expertSkillRatingRepository;
    private final ExpertSkillOptionRepository expertSkillOptionRepository;
    private final SearchRepository searchRepository;

    @Override
    public boolean create(List<CreateExpertSkillRatingRequestDTO> requestList, long feedbackId) {
        if (requestList == null) {
            return false;
        }

        //todo: check again
        for (CreateExpertSkillRatingRequestDTO r : requestList) {
            if (r.getRating() < 1 || r.getRating() > 5) {
                continue;
            }

            ExpertSkillRating expertSkillRating = expertSkillRatingRepository.findByBookingCustomerFeedbackIdAndExpertSkillOptionId(feedbackId, r.getExpertSkillOptionId());
            if (expertSkillRating != null) {
                continue;
            }

            ExpertSkillRating build = ExpertSkillRating
                    .builder()
                    .expertSkillOption(new ExpertSkillOption(r.getExpertSkillOptionId()))
                    .rating(r.getRating())
                    .bookingCustomerFeedbackId(feedbackId)
                    .comment(r.getComment())
                    .build();
            expertSkillRatingRepository.save(build);
        }

        return true;
    }

    @Override
    public PageResponseDTO<ExpertSkillRatingResponseDTO> getByExpert(int pageNumber, int pageSize, String sortBy, long expertId, long expertSkillOptionId) {
        checkExpertSkillOption(expertSkillOptionId, expertId);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).descending());
        Page<ExpertSkillRating> expertSkillRatingPage = expertSkillRatingRepository.findByExpertSkillOptionId(expertSkillOptionId, pageable);
        return new PageResponseDTO<>(expertSkillRatingPage.map(ExpertSkillRatingMapper::toDto).toList(), expertSkillRatingPage.getTotalPages());
    }

    @Override
    public List<ExpertSkillRatingTotalRatingResponseDTO> getTotalRating(long expertSkillOptionId) {
        ExpertSkillOption expertSkillOption = expertSkillOptionRepository.findById(expertSkillOptionId);
        if (expertSkillOption == null) {
            throw new CustomException("not found expert skill option");
        }

        return expertSkillRatingRepository.getTotalRating(expertSkillOptionId);
    }

    @Override
    public PageResponseDTO<ExpertSkillRatingResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search) {
        Page<ExpertSkillRating> expertSkillRatingPage = searchRepository.getEntitiesPage(ExpertSkillRating.class, pageNumber, pageSize, sortBy, search);
        return new PageResponseDTO<>(expertSkillRatingPage.map(ExpertSkillRatingMapper::toDto).toList(), expertSkillRatingPage.getTotalPages());
    }

    private void checkExpertSkillOption(long expertSkillOptionId, long expertId) {
        ExpertSkillOption expertSkillOption = expertSkillOptionRepository.findById(expertSkillOptionId);
        if (expertSkillOption == null) {
            throw new CustomException("not found expert skill option");
        }

        if (expertSkillOption.getExpertId() != expertId) {
            throw new CustomException("skill option not belong to expert");
        }
    }
}
