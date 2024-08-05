package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ExpertSkillOption;
import com.example.gcj.Repository_Layer.model.ExpertSkillRating;
import com.example.gcj.Repository_Layer.repository.ExpertSkillRatingRepository;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertSkillRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertSkillRatingServiceImpl implements ExpertSkillRatingService {
    private final ExpertSkillRatingRepository expertSkillRatingRepository;

    @Override
    public boolean create(List<CreateExpertSkillRatingRequestDTO> requestList, long feedbackId) {
        if (requestList == null) {
            return false;
        }

        //todo: check again
        for (CreateExpertSkillRatingRequestDTO r : requestList) {
            ExpertSkillRating expertSkillRating = expertSkillRatingRepository.findByBookingCustomerFeedbackIdAndExpertSkillOptionId(feedbackId, r.getExpertSkillOptionId());
            if (expertSkillRating != null) {
                continue;
            }
            ExpertSkillRating build = ExpertSkillRating
                    .builder()
                    .expertSkillOption(new ExpertSkillOption(r.getExpertSkillOptionId()))
                    .rating(r.getRating())
                    .bookingCustomerFeedbackId(feedbackId)
                    .build();
            expertSkillRatingRepository.save(build);
        }

        return true;
    }
}
