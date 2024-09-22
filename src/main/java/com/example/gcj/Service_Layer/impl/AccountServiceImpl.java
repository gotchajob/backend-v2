package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.*;
import com.example.gcj.Repository_Layer.repository.AccountRepository;
import com.example.gcj.Repository_Layer.repository.CustomerRepository;
import com.example.gcj.Repository_Layer.repository.RevenueRepository;
import com.example.gcj.Repository_Layer.repository.TransactionRepository;
import com.example.gcj.Service_Layer.dto.account.CreditRequestDTO;
import com.example.gcj.Service_Layer.dto.account.DebitRequestDTO;
import com.example.gcj.Service_Layer.dto.account.RejectWithDrawRequestDTO;
import com.example.gcj.Service_Layer.service.AccountService;
import com.example.gcj.Service_Layer.service.PolicyService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.enums.PolicyKey;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.status.TransactionStatus;
import com.example.gcj.Shared.util.status.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
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

        Double minWithdraw = policyService.getByKey(PolicyKey.WITHDRAW_MIN, Double.class);
        if (request.getAmount() < minWithdraw) {
            throw new CustomException("min withdraw is " + minWithdraw + "vnd");
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
                .status(TransactionStatus.PROCESSING)
                .description(request.getDescription())
                .balanceAfterTransaction(account.getBalance())
                .transactionTypeId(TransactionType.WITHDRAW)
                .referId(request.getBankInfoId())
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

    private Account getCurrentAccount() {
        long userId = userService.getCurrentUserId();
        Account account = getAccountByUserId(userId);
        return account;
    }

    @Override
    public boolean sendMoneyToExpert(long userId, long bookingId) {
        Account account = getAccountByUserId(userId);

        Transaction customerTransaction = transactionRepository.findByReferIdAndTransactionTypeId(bookingId, TransactionType.PAY_FOR_SERVICE);
        if (customerTransaction == null) {
            return false;
        }

        Double moneyPayForExpert = customerTransaction.getAmount();
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

    @Override
    public boolean completeWithDraw(long transactionId) {
        Transaction transaction = checkTransactionWithDraw(transactionId);

        transaction.setStatus(TransactionStatus.COMPLETE);
        transactionRepository.save(transaction);
        return true;
    }

    @Override
    public boolean rejectWithDraw(long transactionId, RejectWithDrawRequestDTO request) {
        Transaction transaction = checkTransactionWithDraw(transactionId);

        transaction.setStatus(TransactionStatus.FAIL);
        transaction.setDescription(request.getReason());
        transactionRepository.save(transaction);

        Account account = accountRepository.findById(transaction.getAccountId());
        if (account == null) {
            throw new CustomException("not found account");
        }

        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepository.save(account);

        return true;
    }

    @Override
    public boolean bookingPayment(double cost, long bookingId) {
        Account currentAccount = getCurrentAccount();
        if (cost > currentAccount.getBalance()) {
            throw new CustomException("not enough money in account");
        }

        currentAccount.setBalance(currentAccount.getBalance() - cost);
        accountRepository.save(currentAccount);

        Transaction transaction = Transaction
                .builder()
                .accountId(currentAccount.getId())
                .description("pay for booking with cost " + cost)
                .balanceAfterTransaction(currentAccount.getBalance())
                .amount(cost)
                .referId(bookingId)
                .transactionTypeId(TransactionType.PAY_FOR_SERVICE)
                .status(TransactionStatus.COMPLETE)
                .build();
        transactionRepository.save(transaction);

        return true;
    }

    @Override
    public boolean refundWhenCancelBooking(long customerId, long bookingId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return false;
        }

        Account account = accountRepository.findByUserId(customer.getUserId());

        Transaction transaction = transactionRepository.findByReferIdAndTransactionTypeId(bookingId, TransactionType.PAY_FOR_SERVICE);
        if (transaction == null) {
            return false;
        }

        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepository.save(account);

        transaction.setStatus(TransactionStatus.FAIL);
        transaction.setDescription("fail because booking is cancel");
        transactionRepository.save(transaction);

        return true;
    }

    @Override
    public double getCurrentBalance() {
        Account currentAccount = getCurrentAccount();
        return currentAccount.getBalance();
    }

    private Transaction checkTransactionWithDraw(long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId);
        if (transaction == null) {
            throw new CustomException("not found transaction with id " + transactionId);
        }
        if (transaction.getTransactionTypeId() != TransactionType.WITHDRAW) {
            throw new CustomException("transaction type is not withdraw");
        }

        if (transaction.getStatus() != TransactionStatus.PROCESSING) {
            throw new CustomException("transaction is not processing");
        }

        return transaction;
    }
}
