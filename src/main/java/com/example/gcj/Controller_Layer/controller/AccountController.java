package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.External_Service.VnPayService;
import com.example.gcj.Service_Layer.dto.account.*;
import com.example.gcj.Service_Layer.service.AccountService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
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

    @PatchMapping("/withdrawn/{transactionId}/complete")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> completeWithDraw(
            @PathVariable long transactionId
    ) {
        accountService.completeWithDraw(transactionId);
        return Response.ok(null);
    }

    @PatchMapping("/withdrawn/{transactionId}/reject")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> completeWithDraw(
            @PathVariable long transactionId,
            @RequestBody RejectWithDrawRequestDTO request
    ) {
        accountService.rejectWithDraw(transactionId, request);
        return Response.ok(null);
    }
}
