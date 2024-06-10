package com.example.gcj.service.impl;

import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Policy;
import com.example.gcj.repository.PolicyRepository;
import com.example.gcj.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
