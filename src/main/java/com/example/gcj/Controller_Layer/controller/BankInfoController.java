package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.bank_info.*;
import com.example.gcj.Service_Layer.service.AccountService;
import com.example.gcj.Service_Layer.service.BankInfoService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankInfo")
@RequiredArgsConstructor
public class BankInfoController { 
    private final BankInfoService bankInfoService;
    private final AccountService accountService;

    @GetMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "finish. role: staff")
    public Response<List<BankInfoListResponseDTO>> get(
    ) {
        List<BankInfoListResponseDTO> response = bankInfoService.get();
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(description = "finish. role: expert")
    public Response<List<BankInfoListResponseDTO>> getByCurrent(
    ) {
        long accountId = accountService.getCurrentAccountId();
        List<BankInfoListResponseDTO> response = bankInfoService.getByAccount(accountId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<BankInfoResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        BankInfoResponseDTO response = bankInfoService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<CreateBankInfoResponseDTO> create(
            @RequestBody @Valid CreateBankInfoRequestDTO request
    ) {
        long accountId = accountService.getCurrentAccountId();
        CreateBankInfoResponseDTO response = bankInfoService.create(request, accountId);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateBankInfoRequestDTO request
    ) {
        long accountId = accountService.getCurrentAccountId();
        bankInfoService.update(id, request, accountId);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long accountId = accountService.getCurrentAccountId();
        bankInfoService.delete(id, accountId);
        return Response.ok(null);
    }
}
