package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.payment_info.PaymentInfoResponseDTO;
import com.example.gcj.Service_Layer.service.PaymentInfoService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-info")
@RequiredArgsConstructor
public class PaymentInfoController {
    private final PaymentInfoService paymentInfoService;
    @GetMapping("")
    @Operation(description = "coming soon")
    public Response<String> get(
    ) {

        return Response.ok(null);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<PaymentInfoResponseDTO> getById(
            @PathVariable long id
    ) {
        PaymentInfoResponseDTO response = paymentInfoService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Operation(description = "coming soon")
    public Response<String> create(

    ) {

        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "coming soon")
    public Response<String> delete(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }


}
