package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertProfileRequestDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.ExpertAccountResponse;

import java.util.List;

public interface ExpertService {
    List<ExpertMatchListResponseDTO> expertMatch(Integer main, List<Long> skillOptionIds, List<String> nations, int yearExperience);
    List<ExpertMatchListResponseDTO> newExpertMatch(Integer main, List<Long> skillOptionIds, List<String> nations, Integer yearExperience);
    PageResponseDTO<ExpertAccountResponse> getExpert(int pageNumber, int pageSize, String sortBy, String ...search);
    ExpertAccountResponse getExpert(long id);
    ExpertAccountResponse getByCurrent();

    boolean updateExpert(long id, UpdateExpertRequestDTO request);
    boolean verifyExpert(long expertId);
    long getCurrentExpertId();
    String getEmailByExpertId(long expertId);

    boolean updatePrice(long expertId, double cost);
    boolean updateExpertPoint(long expertId, int point);

    boolean updateProfile(long expertId, UpdateExpertProfileRequestDTO request);

    boolean updateCurrentExpertStatus(int status);
}
