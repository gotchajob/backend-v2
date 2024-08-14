package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.repository.CvReactionRepository;
import com.example.gcj.Service_Layer.service.CvReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CvReactionServiceImpl implements CvReactionService {
    private final CvReactionRepository cvReactionRepository;

}
