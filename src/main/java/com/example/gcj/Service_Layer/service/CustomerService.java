package com.example.gcj.Service_Layer.service;

public interface CustomerService {
    long getCurrentCustomerId();

    boolean buyBookingService();
    boolean checkBuyService();

    String getEmailById(long customerId);
}
