package com.example.gcj.Service_Layer.service;

import com.example.gcj.Repository_Layer.model.Customer;

public interface CustomerService {
    long getCurrentCustomerId();
    int getMaxCv(long customerId);
    Customer createDefaultCustomer(long userId);
}
