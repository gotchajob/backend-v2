package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;

import java.util.List;

public interface ExpertNationSupportService {
    boolean create(long expertId, List<String> nations);
    List<ExpertNationSupportResponseDTO> getByExpertId(long expertId);
    boolean deleteAllByExpertId(long expertId);
}
