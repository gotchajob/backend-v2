package com.example.gcj.service.impl;

import com.example.gcj.repository.PolicyRepository;
import com.example.gcj.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepository policyRepository;

}
