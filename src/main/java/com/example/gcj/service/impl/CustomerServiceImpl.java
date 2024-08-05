package com.example.gcj.service.impl;

import com.example.gcj.dto.user.UserBookingInfoResponseDTO;
import com.example.gcj.enums.PolicyKey;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Account;
import com.example.gcj.model.BookingTicket;
import com.example.gcj.model.Customer;
import com.example.gcj.model.Transaction;
import com.example.gcj.repository.AccountRepository;
import com.example.gcj.repository.CustomerRepository;
import com.example.gcj.repository.TransactionRepository;
import com.example.gcj.service.BookingTicketService;
import com.example.gcj.service.CustomerService;
import com.example.gcj.service.PolicyService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.status.TransactionStatus;
import com.example.gcj.util.type.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final BookingTicketService bookingTicketService;
    private final PolicyService policyService;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public long getCurrentCustomerId() {
        Customer customer = getCurrentCustomer();

        return customer.getId();
    }

    @Override
    public boolean buyBookingService() {
        Customer customer = getCurrentCustomer();

        if (bookingTicketService.hadTicket(customer.getId())) {
            throw new CustomException("You have already purchased the service");
        }

        Account account = accountRepository.findByUserId(customer.getUserId());
        if (account == null) {
            throw new CustomException("not found account with userId " + customer.getUserId());
        }

        long priceBooking = policyService.getByKey(PolicyKey.BOOKING_PRICE, Long.class);
        if (account.getBalance() < priceBooking) {
            throw new CustomException("not enough balance to buy service");
        }

        account.setBalance(account.getBalance() - priceBooking);
        accountRepository.save(account);

        BookingTicket bookingTicket = bookingTicketService.create(customer.getId());

        Transaction transaction = Transaction
                .builder()
                .accountId(account.getId())
                .amount(priceBooking)
                .status(TransactionStatus.COMPLETE)
                .description("buy booking service")
                .transactionTypeId(TransactionType.PAY_FOR_SERVICE)
                .referId(bookingTicket.getId())
                .build();
        transactionRepository.save(transaction);

        return true;
    }

    @Override
    public boolean checkBuyService() {
        Customer customer = getCurrentCustomer();
        if (!bookingTicketService.hadTicket(customer.getId())) {
            throw new CustomException("not found any ticket booking");
        }

        return true;
    }

    @Override
    public String getEmailById(long customerId) {
        UserBookingInfoResponseDTO customerInfo = customerRepository.customerInfo(customerId);
        return customerInfo.getEmail();
    }

    private Customer getCurrentCustomer() {
        long userId = userService.getCurrentUserId();
        Customer customer = customerRepository.findByUserId(userId);
        if (customer == null) {
            customer = createDefaultCustomer(userId);
        }

        return customer;
    }

    private Customer createDefaultCustomer(long userId) {
        Customer build = Customer
                .builder()
                .userId(userId)
                .maxAllowCv(5)
                .numberBooking(0)
                .build();

        return customerRepository.save(build);
    }
}
