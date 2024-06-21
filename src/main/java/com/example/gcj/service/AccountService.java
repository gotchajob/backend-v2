package com.example.gcj.service;

import com.example.gcj.dto.account.CreditRequestDTO;
import com.example.gcj.dto.account.DebitRequestDTO;
import com.example.gcj.model.Account;

public interface AccountService {
    double getBalanceByUserId(long userId);
    boolean credit(long userId, CreditRequestDTO request);
    boolean debit(long userId, DebitRequestDTO request);
    Account getAccountByUserId(long userId);
}
