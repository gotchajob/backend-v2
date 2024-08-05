package com.example.gcj.controller;

import com.example.gcj.dto.transaction_type.TransactionTypeListResponseDTO;
import com.example.gcj.service.TransactionTypeService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
