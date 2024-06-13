package com.example.gcj.service.impl;

import com.example.gcj.repository.AccountRepository;
import com.example.gcj.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

}
