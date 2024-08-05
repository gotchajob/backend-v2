package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.Service_Layer.service.TransactionTypeService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction-type")
@RequiredArgsConstructor
public class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    @GetMapping("")
    @Operation(description = "finish. role: n/a")
    public Response<List<TransactionTypeListResponseDTO>> get(
    ) {
        List<TransactionTypeListResponseDTO> responseDTOS = transactionTypeService.getAll();
        return Response.ok(responseDTOS);
    }
}
