package com.example.gcj.controller;

import com.example.gcj.dto.expert_register_request.*;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expert-register-request")
@RequiredArgsConstructor
@Tag(name = "Expert Register Request Controller")
public class ExpertRegisterRequestController {
    private final ExpertRegisterRequestService expertRegisterRequestService;

    @PostMapping("")
    @Operation(summary = "admin, user")
    public Response<String> mentorRegisterRequest(
            @RequestBody CreateExpertRegisterRequestDTO request
    ) {
        expertRegisterRequestService.create(request.getEmail());
        return Response.ok(null);
    }

    @GetMapping("")
    public Response<GetExpertRegisterRequestResponseDTO> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int limit
    ) {
        GetExpertRegisterRequestResponseDTO responseDTO = expertRegisterRequestService.get(page, limit);
        return Response.ok(responseDTO);
    }

    @PatchMapping("/{id}/approve")
    public Response<String> approveRegister(
            @PathVariable long id,
            @RequestBody ApproveExpertRegisterRequestDTO requestDTO
    ) {
        expertRegisterRequestService.approveRegister(id, requestDTO.getUrl());
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject")
    public Response<String> rejectRegister(
            @PathVariable long id,
            @RequestBody(required = false) RejectExpertRegisterRequestDTO requestDTO
    ) {
        expertRegisterRequestService.rejectRegister(id, requestDTO.getNote());
        return Response.ok(null);
    }

    @PostMapping("/ban")
    public Response<String> banEmail(
            @RequestBody BanEmailRequestDTO request
    ) {
        //add email to black list
        //delete all request
        //check black list when create
        return Response.ok(null);
    }

}
