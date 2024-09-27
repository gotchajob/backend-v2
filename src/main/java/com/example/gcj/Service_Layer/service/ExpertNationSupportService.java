package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.expert_nation_support.CreateNationSupportRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportListRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportRequestDTO;

import java.util.List;

public interface ExpertNationSupportService {
    boolean create(long expertId, List<String> nations);
    List<ExpertNationSupportResponseDTO> getByExpertId(long expertId);
    boolean deleteAllByExpertId(long expertId);

    boolean createNation(long expertId, CreateNationSupportRequestDTO request);

    boolean update(long expertId, long id, UpdateNationSupportRequestDTO request);

    boolean delete(long expertId, long id);

    boolean update(long expertId, UpdateNationSupportListRequestDTO nations);
}
