package com.example.gcj.service.impl;

import com.example.gcj.repository.SkillRepository;
import com.example.gcj.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;


}
