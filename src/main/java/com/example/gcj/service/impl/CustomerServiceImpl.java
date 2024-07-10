package com.example.gcj.service.impl;

import com.example.gcj.exception.CustomException;
import com.example.gcj.repository.CustomerRepository;
import com.example.gcj.service.CustomerService;
import com.example.gcj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final CustomerRepository customerRepository;

    @Override
    public long getCurrentCustomerId() {
        long userId = userService.getCurrentUserId();
        Long customerId = customerRepository.getIdByUserId(userId);
        if (customerId == null) {
            throw new CustomException("not found customer");
        }

        return customerId;
    }
}
