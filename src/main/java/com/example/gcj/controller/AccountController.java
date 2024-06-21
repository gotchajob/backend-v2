package com.example.gcj.controller;

import com.example.gcj.dto.account.CreditRequestDTO;
import com.example.gcj.dto.account.DebitRequestDTO;
import com.example.gcj.dto.account.GetBalanceAccountResponseDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.TransactionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping("/current/balance")
    @Secured({Role.EXPERT, Role.USER})
    @Operation(description = "role: user, expert")
    public Response<GetBalanceAccountResponseDTO> getBalance(
    ) {
        long userId = userService.getCurrentUserId();
        double balance = accountService.getBalanceByUserId(userId);

        return Response.ok(GetBalanceAccountResponseDTO.builder().balance(balance).build());
    }

    @GetMapping("/current/transaction")
    @Secured({Role.USER, Role.EXPERT})
    @Operation(description = "role: user, expert")
    public Response<PageResponseDTO<TransactionResponseDTO>> getTransaction(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize
    ) {
        long userId = userService.getCurrentUserId();
        PageResponseDTO<TransactionResponseDTO> response = transactionService.getByUserId(userId, pageNumber, pageSize);
        return Response.ok(response);
    }

    @PatchMapping("/current/credit")
    @Secured(Role.USER)
    @Operation(summary = "nap tien", description = "role: user")
    public Response<String> credit(
            @RequestBody CreditRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        accountService.credit(userId, request);
        return Response.ok(null);
    }

    @PatchMapping("/current/debit")
    @Secured(Role.EXPERT)
    @Operation(summary = "rut tien", description = "role: expert")
    public Response<String> debit(
            @RequestBody DebitRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        accountService.debit(userId, request);
        return Response.ok(null);
    }
}
