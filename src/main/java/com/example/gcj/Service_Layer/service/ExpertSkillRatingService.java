package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingTotalRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;

import java.util.List;

public interface ExpertSkillRatingService {
    boolean create(List<CreateExpertSkillRatingRequestDTO> requestList, long feedbackId);


    PageResponseDTO<ExpertSkillRatingResponseDTO> getByExpert(int pageNumber, int pageSize, String sortBy, long expertId, long expertSkillOptionId);

    List<ExpertSkillRatingTotalRatingResponseDTO> getTotalRating(long expertSkillOptionId);

    PageResponseDTO<ExpertSkillRatingResponseDTO> get(int pageNumber, int pageSize, String sortBy, String... search);
}
