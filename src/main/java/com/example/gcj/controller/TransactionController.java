package com.example.gcj.controller;

import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.transaction.TransactionCurrentListResponseDTO;
import com.example.gcj.dto.transaction.TransactionResponseDTO;
import com.example.gcj.service.AccountService;
import com.example.gcj.service.TransactionService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping("")
    //@Secured(Role.STAFF)
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
}
