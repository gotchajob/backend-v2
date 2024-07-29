package com.example.gcj.controller;

import com.example.gcj.dto.cv.*;
import com.example.gcj.service.CustomerService;
import com.example.gcj.service.CvService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cv")
@RequiredArgsConstructor
public class CvController {
    private final CvService cvService;
    private final CustomerService customerService;

    @GetMapping("/current")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<List<CVListResponseDTO>> getListByCurrentUser(

    ) {
        long customerId = customerService.getCurrentCustomerId();
        List<CVListResponseDTO> response = cvService.getByCustomerId(customerId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CvResponseDTO> getById(
            @PathVariable long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        CvResponseDTO response = cvService.getById(customerId, id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CreateCvResponseDTO> create(
            @RequestBody CreateCvRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        CreateCvResponseDTO response =  cvService.create(customerId, request);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> updateCv(
            @PathVariable long id,
            @RequestBody UpdateCvRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvService.update(customerId, id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> deleteCv(
            @PathVariable long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvService.delete(customerId, id);
        return Response.ok(null);
    }

}
