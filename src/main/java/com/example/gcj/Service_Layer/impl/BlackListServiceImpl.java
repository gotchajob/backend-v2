package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.repository.BlackListRepository;
import com.example.gcj.Service_Layer.service.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository blackListRepository;

}
