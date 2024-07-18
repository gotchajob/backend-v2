package com.example.gcj.service.impl;

import com.example.gcj.dto.policy.CreatePolicyRequestDTO;
import com.example.gcj.dto.policy.PolicyListResponseDTO;
import com.example.gcj.dto.policy.PolicyResponseDTO;
import com.example.gcj.dto.policy.UpdatePolicyRequestDTO;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Policy;
import com.example.gcj.repository.PolicyRepository;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.mapper.PolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepository policyRepository;

    @Override
    public <T> T getByKey(PolicyKey key, Class<T> type) {
        if (key == null) {
            throw new CustomException("Fail to load policy. Key is null");
        }

        if (!isExistKey(key)) {
            throw new CustomException("Fail to load policy. Key does not exist. Key: " + key);
        }

        Policy policy = policyRepository.getByKey(key.name());
        if (policy == null) {
            throw new CustomException("Fail to load policy from database. Key: " + key);
        }

        String value = policy.getValue();

        try {
            if (type == Integer.class) {
                return type.cast(Integer.parseInt(value));
            } else if (type == Long.class) {
                return type.cast(Long.parseLong(value));
            } else if (type == Double.class) {
                return type.cast(Double.parseDouble(value));
            } else if (type == String.class) {
                return type.cast(value);
            } else {
                throw new CustomException("Unsupported type: " + type.getName());
            }
        } catch (NumberFormatException e) {
            throw new CustomException("Failed to parse policy value. Key: " + key + ", Value: " + value + ", Type: " + type.getName());
        }
    }

    @Override
    public boolean update(long id, UpdatePolicyRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        Policy policy = policyRepository.findById(id);
        if (policy == null) {
            throw new CustomException("not found policy with id " + id);
        }

        policy.setValue(request.getValue());
        policyRepository.save(policy);
        return true;
    }

    @Override
    public boolean update(PolicyKey key, String value) {
        if (key == null || value == null) {
            throw new CustomException("bad request");
        }

        Policy byKey = policyRepository.getByKey(key.name());
        if (byKey == null) {
            throw new CustomException("not found policy with key " + key.name());
        }

        byKey.setValue(value);
        policyRepository.save(byKey);

        return true;
    }

    @Override
    public List<PolicyListResponseDTO> getAll() {
        List<Policy> policyList = policyRepository.findAll();
        return policyList.stream().map(PolicyMapper::toDto).toList();
    }

    @Override
    public boolean create(CreatePolicyRequestDTO request) {
        if (request == null) {
            throw new CustomException("bad request");
        }

        Policy build = Policy
                .builder()
                .key(request.getKey())
                .value(request.getValue())
                .description(request.getDescription())
                .build();
        policyRepository.save(build);
        return true;
    }

    @Override
    public PolicyResponseDTO getById(long id) {
        Policy policy = policyRepository.findById(id);
        if (policy == null) {
            throw new CustomException("not found policy with id " + id);
        }

        return PolicyMapper.toDtoDetail(policy);
    }

    @Override
    public PolicyResponseDTO getByKey(String key) {
        Policy policy = policyRepository.getByKey(key);
        if (policy == null) {
            throw new CustomException("not found policy with key " + key);
        }

        return PolicyMapper.toDtoDetail(policy);
    }

    private boolean isExistKey(PolicyKey key) {
        if (key == null) {
            return false;
        }

        for (PolicyKey value: PolicyKey.values()) {
            if (value == key) {
                return true;
            }
        }
        return false;
    }
}
