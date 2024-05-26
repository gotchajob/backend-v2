package com.example.gcj.controller;

import com.example.gcj.dto.expert_register_request.ApproveExpertRegisterRequestDTO;
import com.example.gcj.dto.expert_register_request.CreateExpertRegisterRequestDTO;
import com.example.gcj.dto.expert_register_request.GetExpertRegisterRequestResponseDTO;
import com.example.gcj.dto.expert_register_request.RejectExpertRegisterRequestDTO;
import com.example.gcj.service.ExpertRegisterRequestService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/epxert-register-request")
@RequiredArgsConstructor
@Tag(name = "Expert Register Request Controller")
public class ExpertRegisterRequestController {
    private final ExpertRegisterRequestService expertRegisterRequestService;

    @PostMapping("")
    @Operation(summary = "admin, user")
    public ResponseEntity<Response<String>> mentorRegisterRequest(
            @RequestBody CreateExpertRegisterRequestDTO request
    ) {
        try {
            expertRegisterRequestService.create(request.getEmail());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<Response<GetExpertRegisterRequestResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int limit
    ) {
        try {
            GetExpertRegisterRequestResponseDTO responseDTO = expertRegisterRequestService.get(page, limit);
            return Response.success(responseDTO);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Response<String>> approveRegister(
            @PathVariable long id,
            @RequestBody ApproveExpertRegisterRequestDTO requestDTO
    ) {
        try {
            expertRegisterRequestService.approveRegister(id, requestDTO.getUrl());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Response<String>> rejectRegister(
            @PathVariable long id,
            @RequestBody(required = false) RejectExpertRegisterRequestDTO requestDTO
    ) {
        try {
            expertRegisterRequestService.rejectRegister(id, requestDTO.getNote());
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
