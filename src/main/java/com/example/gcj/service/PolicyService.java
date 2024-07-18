package com.example.gcj.service;

import com.example.gcj.dto.policy.CreatePolicyRequestDTO;
import com.example.gcj.dto.policy.PolicyListResponseDTO;
import com.example.gcj.dto.policy.PolicyResponseDTO;
import com.example.gcj.dto.policy.UpdatePolicyRequestDTO;
import com.example.gcj.enums.PolicyKey;

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
