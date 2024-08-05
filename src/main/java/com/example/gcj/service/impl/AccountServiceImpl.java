package com.example.gcj.service.impl;

import com.example.gcj.dto.account.CreditRequestDTO;
import com.example.gcj.dto.account.DebitRequestDTO;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Account;
import com.example.gcj.model.Revenue;
import com.example.gcj.model.Transaction;
import com.example.gcj.model.User;
import com.example.gcj.repository.AccountRepository;
import com.example.gcj.repository.RevenueRepository;
import com.example.gcj.repository.TransactionRepository;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.status.TransactionStatus;
import com.example.gcj.util.status.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RevenueRepository revenueRepository;
    private final UserService userService;
    private final PolicyService policyService;

    @Override
    public double getBalanceByUserId(long userId) {
        Account account = getAccountByUserId(userId);

        return account.getBalance();
    }

    @Override
    public boolean deposit(long userId, CreditRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }
        if (request.getAmount() <= 0) {
            throw new CustomException("invalid amount number");
        }

        Account account = getAccountByUserId(userId);

        double newBalance = account.getBalance() + request.getAmount();
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = Transaction
                .builder()
                .accountId(account.getId())
                .amount(request.getAmount())
                .status(TransactionStatus.COMPLETE)
                .description(request.getDescription())
                .transactionTypeId(TransactionType.DEPOSIT)
                .referId(null)
                .build();
        transactionRepository.save(transaction);

        return true;
    }

    @Override
    public boolean deposit(long accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new CustomException("not found account with id " + accountId);
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean withdrawn(long userId, DebitRequestDTO request) {
        if (request == null) {
            throw new CustomException("invalid request");
        }

        double amount = request.getAmount();
        if (amount <= 0) {
            throw new CustomException("invalid amount number");
        }

        Account account = getAccountByUserId(userId);

        if (account.getBalance() < amount) {
            throw new CustomException("amount to withdrawn is larger balance");
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = Transaction
                .builder()
                .accountId(account.getId())
                .amount(amount)
                .status(TransactionStatus.COMPLETE)
                .description(request.getDescription())
                .transactionTypeId(TransactionType.WITHDRAW)
                .referId(null)
                .build();
        transactionRepository.save(transaction);

        return true;
    }

    public Account createDefault(long userId) {
        Account account = Account.builder().user(new User(userId)).build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByUserId(long userId) {
        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            return createDefault(userId);
        }

        return account;
    }

    @Override
    public long getCurrentAccountId() {
        long userId = userService.getCurrentUserId();
        Account account = getAccountByUserId(userId);
        return account.getId();
    }

    @Override
    public boolean sendMoneyToExpert(long userId, long bookingId) {
        Account account = getAccountByUserId(userId);
        Double moneyPayForExpert = policyService.getByKey(PolicyKey.MONEY_BOOKING_PAY_FOR_EXPERT, Double.class);
        int revenuePercent = policyService.getByKey(PolicyKey.REVENUE_PERCENT, Integer.class);

        double revenueMoney = (moneyPayForExpert/100) * revenuePercent;
        Transaction transaction = Transaction
                .builder()
                .accountId(account.getId())
                .amount(moneyPayForExpert - revenueMoney)
                .transactionTypeId(TransactionType.RECEIVE_FROM_SERVICE)
                .description("receive money when complete a booking, booking id " + bookingId + ". system revenue is " + revenueMoney)
                .status(TransactionStatus.COMPLETE)
                .referId(bookingId)
                .build();
        Transaction transactionSave = transactionRepository.save(transaction);
        Revenue revenue = Revenue
                .builder()
                .amount(revenueMoney)
                .percent(revenuePercent)
                .transactionId(transactionSave.getId())
                .build();
        revenueRepository.save(revenue);

        account.setBalance(account.getBalance() + moneyPayForExpert - revenueMoney);
        accountRepository.save(account);

        return true;
    }
}
