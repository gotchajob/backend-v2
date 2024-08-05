package com.example.gcj.service;

public interface CustomerService {
    long getCurrentCustomerId();

    boolean buyBookingService();
    boolean checkBuyService();

    String getEmailById(long customerId);
}
