package com.example.gcj.service;

import com.example.gcj.dto.account.CreditRequestDTO;
import com.example.gcj.dto.account.DebitRequestDTO;
import com.example.gcj.model.Account;

public interface AccountService {
    double getBalanceByUserId(long userId);
    boolean deposit(long userId, CreditRequestDTO request);
    boolean deposit(long accountId, double amount);
    boolean withdrawn(long userId, DebitRequestDTO request);
    Account getAccountByUserId(long userId);

    long getCurrentAccountId();
    boolean sendMoneyToExpert(long userId, long bookingId);
}
