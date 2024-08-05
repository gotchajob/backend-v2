package com.example.gcj.service;

import com.example.gcj.dto.expert_skill_rating.CreateExpertSkillRatingRequestDTO;

import java.util.List;

public interface ExpertSkillRatingService {
    boolean create(List<CreateExpertSkillRatingRequestDTO> requestList, long feedbackId);
}
