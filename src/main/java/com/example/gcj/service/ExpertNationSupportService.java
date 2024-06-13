package com.example.gcj.service;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;

import java.util.List;

public interface ExpertNationSupportService {
    boolean create(long expertId, List<String> nations);
    List<ExpertNationSupportResponseDTO> getByExpertId(long expertId);
    boolean deleteAllByExpertId(long expertId);
}
