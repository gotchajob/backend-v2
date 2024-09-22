package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.account.CreditRequestDTO;
import com.example.gcj.Service_Layer.dto.account.DebitRequestDTO;
import com.example.gcj.Repository_Layer.model.Account;
import com.example.gcj.Service_Layer.dto.account.RejectWithDrawRequestDTO;

public interface AccountService {
    double getBalanceByUserId(long userId);
    boolean deposit(long userId, CreditRequestDTO request);
    boolean deposit(long accountId, double amount);
    boolean withdrawn(long userId, DebitRequestDTO request);
    Account getAccountByUserId(long userId);

    long getCurrentAccountId();
    boolean sendMoneyToExpert(long userId, long bookingId);

    boolean completeWithDraw(long transactionId);

    boolean rejectWithDraw(long transactionId, RejectWithDrawRequestDTO request);

    boolean bookingPayment(double cost, long bookingId);

    boolean refundWhenCancelBooking(long customerId, long bookingId);

    double getCurrentBalance();
}
