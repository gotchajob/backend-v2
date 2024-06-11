package com.example.gcj.service;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;

import java.util.List;

public interface ExpertService {
    List<ExpertMatchListResponseDTO> expertMatch(Long categoryId, List<Long> skillOptionIds, List<String> nations, int yearExperience);

    PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String filter);
    PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String ...search);
    ExpertAccountResponse getExpert(long id);

    boolean updateExpert(long id, UpdateExpertRequestDTO request);
    boolean verifyExpert(long expertId);
}
