package com.example.gcj.controller;

import com.example.gcj.dto.expert_register_request.*;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.CreateExpertAccountRequestDTO;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.service.UserService;
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
    private final UserService userService;

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

    @PostMapping("/create_form")
    public Response<String> createForm(
            @RequestBody CreateExpertAccountRequestDTO request
    ) {
        userService.createExpertAccount(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/approve-form")
    public Response<String> approveRegisterForm(
            @PathVariable long id
    ) {
        expertRegisterRequestService.approveFormRegister(id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject-form")
    public Response<String> rejectRegister(
            @PathVariable long id,
            @RequestBody(required = false) RejectFromRegisterRequestDTO requestDTO
    ) {
        expertRegisterRequestService.rejectFormRegister(id, requestDTO);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/update-form")
    public Response<String> updateFormRegister(
            @PathVariable long id
    ) {
        //todo: code here
        return Response.ok(null);
    }

    @PostMapping("/{id}/ban")
    public Response<String> banRequest(
            @PathVariable long id
    ) {
        expertRegisterRequestService.banRequest(id);
        return Response.ok(null);
    }

}
