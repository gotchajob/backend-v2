package com.example.gcj.service;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;

import java.util.List;

public interface ExpertService {
    List<ExpertMatchListResponseDTO> expertMatch(Long categoryId, List<Long> skillOptionIds, List<String> nations, int yearExperience);
}
