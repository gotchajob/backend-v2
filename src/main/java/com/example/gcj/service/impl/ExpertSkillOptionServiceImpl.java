package com.example.gcj.service.impl;

import com.example.gcj.repository.ExpertSkillOptionRepository;
import com.example.gcj.service.ExpertSkillOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertSkillOptionServiceImpl implements ExpertSkillOptionService {
    private final ExpertSkillOptionRepository expertSkillOptionRepository;

}
