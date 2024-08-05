package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/check-buy-service")
    @Secured({Role.USER})
    @Operation(description = "check buy booking service")
    public Response<String> checkByService(

    ) {

        customerService.checkBuyService();
        return Response.ok(null);
    }
    @PatchMapping("/buy-service")
    @Secured({Role.USER})
    @Operation(description = "buy service")
    public Response<String> buyService(

    ) {
        //todo: add transaction
        customerService.buyBookingService();
        return Response.ok(null);
    }
}
