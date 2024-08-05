package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;

import java.util.List;

public interface ExpertSkillRatingService {
    boolean create(List<CreateExpertSkillRatingRequestDTO> requestList, long feedbackId);
}
