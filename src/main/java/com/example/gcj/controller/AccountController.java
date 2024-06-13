package com.example.gcj.controller;

import com.example.gcj.service.AccountService;
import com.example.gcj.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountId}/balance")
    public Response<String> getBalance(
        @PathVariable long accountId
    ) {
        return null;
    }

    @GetMapping("/{accountId}/transaction")
    public Response<String> getTransaction(
            @PathVariable long accountId
    ) {
        return null;
    }

    @PatchMapping("/{accountId}/credit")
    public Response<String> credit(
            @PathVariable long accountId
    ) {
        return null;
    }
    @PatchMapping("/{accountId}/debit")
    public Response<String> debit(
            @PathVariable long accountId
    ) {
        return null;
    }


}
