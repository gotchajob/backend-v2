package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Customer;
import com.example.gcj.Repository_Layer.repository.CustomerRepository;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.PolicyService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final PolicyService policyService;


    @Override
    public long getCurrentCustomerId() {
        Customer customer = getCurrentCustomer();

        return customer.getId();
    }

    @Override
    public int getMaxCv(long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new CustomException("not found customer");
        }

        return customer.getMaxAllowCv();
    }


    private Customer getCurrentCustomer() {
        String email = userService.currentUserEmail();
        Customer customer = customerRepository.currentCustomer(email);
        if (customer == null) {
            customer = createDefaultCustomer(userService.getCurrentUserId());
        }

        return customer;
    }

    @Override
    public Customer createDefaultCustomer(long userId) {
        Integer defaultMaxAllowCv = policyService.getByKey(PolicyKey.DEFAULT_MAX_ALLOW_CV, Integer.class);

        Customer build = Customer
                .builder()
                .userId(userId)
                .maxAllowCv(defaultMaxAllowCv)
                .build();

        return customerRepository.save(build);
    }
}
