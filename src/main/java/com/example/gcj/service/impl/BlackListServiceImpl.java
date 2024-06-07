package com.example.gcj.service.impl;

import com.example.gcj.repository.BlackListRepository;
import com.example.gcj.service.BlackListService;
import io.swagger.v3.oas.annotations.servers.Servers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository blackListRepository;

}
