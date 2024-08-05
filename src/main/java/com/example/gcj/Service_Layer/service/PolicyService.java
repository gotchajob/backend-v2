package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.policy.CreatePolicyRequestDTO;
import com.example.gcj.Service_Layer.dto.policy.PolicyListResponseDTO;
import com.example.gcj.Service_Layer.dto.policy.PolicyResponseDTO;
import com.example.gcj.Service_Layer.dto.policy.UpdatePolicyRequestDTO;
import com.example.gcj.Shared.enums.PolicyKey;

import java.util.List;

public interface PolicyService {
    public <T> T getByKey(PolicyKey key, Class<T> type);

    boolean update(long id, UpdatePolicyRequestDTO request);
    boolean update(PolicyKey key, String value);

    List<PolicyListResponseDTO> getAll();

    boolean create(CreatePolicyRequestDTO request);

    PolicyResponseDTO getById(long id);

    PolicyResponseDTO getByKey(String key);
}
