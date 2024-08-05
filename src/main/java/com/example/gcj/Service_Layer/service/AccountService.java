package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.account.CreditRequestDTO;
import com.example.gcj.Service_Layer.dto.account.DebitRequestDTO;
import com.example.gcj.Repository_Layer.model.Account;

public interface AccountService {
    double getBalanceByUserId(long userId);
    boolean deposit(long userId, CreditRequestDTO request);
    boolean deposit(long accountId, double amount);
    boolean withdrawn(long userId, DebitRequestDTO request);
    Account getAccountByUserId(long userId);

    long getCurrentAccountId();
    boolean sendMoneyToExpert(long userId, long bookingId);
}
