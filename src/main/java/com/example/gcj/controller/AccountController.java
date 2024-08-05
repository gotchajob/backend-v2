package com.example.gcj.controller;

import com.example.gcj.dto.account.CreditRequestDTO;
import com.example.gcj.dto.account.DebitRequestDTO;
import com.example.gcj.dto.account.GetBalanceAccountResponseDTO;
import com.example.gcj.dto.account.GetVnPayUrlResponseDTO;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import com.example.gcj.util.service.VnPayService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final VnPayService vnPayService;

    @GetMapping("/current/balance")
    @Secured({Role.EXPERT, Role.USER})
    @Operation(description = "role: user, expert")
    public Response<GetBalanceAccountResponseDTO> getBalance(
    ) {
        long userId = userService.getCurrentUserId();
        double balance = accountService.getBalanceByUserId(userId);

        return Response.ok(GetBalanceAccountResponseDTO.builder().balance(balance).build());
    }

    @PatchMapping("/current/deposit")
    @Secured(Role.USER)
    @Operation(summary = "nap tien", description = "role: user")
    public Response<String> deposit(
            @RequestBody CreditRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        accountService.deposit(userId, request);
        return Response.ok(null);
    }

    @GetMapping("/create-payment")
    @Secured(Role.USER)
    @Operation(summary = "create payment", description = "role: user")
    public Response<GetVnPayUrlResponseDTO> createPaymentUrl(
            HttpServletRequest request,
            @RequestParam String amount,
            @RequestParam(required = false) String bankCode
    ) throws IOException {
        GetVnPayUrlResponseDTO vnPayPayment = vnPayService.createVnPayPayment(request);
        return Response.ok(vnPayPayment);
    }

    @GetMapping("/vn-pay-callback")
    public Response<String> payCallbackHandler(
            HttpServletRequest request
    ) {
        vnPayService.callBackVnPay(request);
        return Response.ok("success");
    }

    @PatchMapping("/current/withdrawn")
    @Secured(Role.EXPERT)
    @Operation(summary = "rut tien", description = "role: expert")
    public Response<String> withdrawn(
            @RequestBody DebitRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        accountService.withdrawn(userId, request);
        return Response.ok(null);
    }
}
