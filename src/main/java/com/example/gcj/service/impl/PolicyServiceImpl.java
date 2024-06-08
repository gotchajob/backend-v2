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
    public int getByKey(PolicyKey key) {
        if (key == null) {
            throw new CustomException("fail to load policy. key is null");
        }

        if (!isExistKey(key)) {
            throw new CustomException("fail to load policy. key is not exist. key: " + key);
        }

        Policy policy = policyRepository.getByKey(key.name());
        if (policy == null) {
            throw new CustomException("fail to load policy from database. key: " + key);
        }

        return policy.getValue();
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
