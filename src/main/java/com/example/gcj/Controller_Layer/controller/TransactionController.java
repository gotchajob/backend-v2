package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.transaction.TransactionCurrentListResponseDTO;
import com.example.gcj.Service_Layer.dto.transaction.TransactionResponseDTO;
import com.example.gcj.Service_Layer.service.AccountService;
import com.example.gcj.Service_Layer.service.TransactionService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import com.example.gcj.Shared.util.type.TransactionType;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "finish. role: staff <br> search: key + (> | < | : | !) + value. example: status!0")
    public Response<PageResponseDTO<TransactionResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt:desc") String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<TransactionResponseDTO> response = transactionService.getAll(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured({Role.EXPERT, Role.USER})
    @Operation(description = "finish. role: expert, user")
    public Response<PageResponseDTO<TransactionCurrentListResponseDTO>> getByCurrent(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize
    ) {
        long accountId = accountService.getCurrentAccountId();
        PageResponseDTO<TransactionCurrentListResponseDTO> response = transactionService.getByAccountId(accountId, pageNumber, pageSize);
        return Response.ok(response);
    }

    @GetMapping("/current/withdraw")
    @Secured({Role.EXPERT})
    @Operation(description = "finish. role: expert")
    public Response<PageResponseDTO<TransactionCurrentListResponseDTO>> withdraw(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) @Min(1) Integer status
    ) {
        long accountId = accountService.getCurrentAccountId();
        String[] search = new String[]{
                "accountId:" + accountId,
                "transactionTypeId:" + TransactionType.WITHDRAW,
                status == null ? "status!0" : "status:" + status
        };

        PageResponseDTO<TransactionCurrentListResponseDTO> response = transactionService.getAllCurrent(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }


}
