package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv.*;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.CustomerService;
import com.example.gcj.Service_Layer.service.CvService;
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

    @GetMapping("/share")
    @Operation(description = "role: n/a")
    public Response<PageResponseDTO<CVListResponseDTO>> getListShared(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<CVListResponseDTO> response = cvService.getShare(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CvResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        CvResponseDTO response = cvService.getById(customerId, id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.USER)
    @Operation(description = "role: user")
    public Response<CreateCvResponseDTO> create(
            @RequestBody @Valid CreateCvRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        CreateCvResponseDTO response =  cvService.create(customerId, request);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> updateCv(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateCvRequestDTO request
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvService.update(customerId, id, request);
        return Response.ok(null);
    }
    @PatchMapping("/{id}/share")
    @Secured({Role.USER})
    @Operation(description = "role: user. if not share -> share. share -> not share")
    public Response<String> updateToShare(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvService.updateToShare(customerId, id);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured({Role.USER})
    @Operation(description = "role: user")
    public Response<String> deleteCv(
            @PathVariable @Min(1) long id
    ) {
        long customerId = customerService.getCurrentCustomerId();
        cvService.delete(customerId, id);
        return Response.ok(null);
    }

}
