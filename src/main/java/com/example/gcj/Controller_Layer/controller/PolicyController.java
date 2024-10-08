package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.policy.CreatePolicyRequestDTO;
import com.example.gcj.Service_Layer.dto.policy.PolicyListResponseDTO;
import com.example.gcj.Service_Layer.dto.policy.PolicyResponseDTO;
import com.example.gcj.Service_Layer.dto.policy.UpdatePolicyRequestDTO;
import com.example.gcj.Service_Layer.service.PolicyService;
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
@RequestMapping("/policy")
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<PolicyListResponseDTO>> get(
    ) {
        List<PolicyListResponseDTO> response = policyService.getAll();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "")
    public Response<PolicyResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        PolicyResponseDTO responseDTO = policyService.getById(id);
        return Response.ok(responseDTO);
    }
    @GetMapping("/key/{key}")
    @Operation(description = "")
    public Response<PolicyResponseDTO> getByKey(
            @PathVariable String key
    ) {
        PolicyResponseDTO responseDTO = policyService.getByKey(key);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Secured(Role.ADMIN)
    @Operation(description = "")
    public Response<String> create(
            @RequestBody @Valid CreatePolicyRequestDTO request
    ) {
        policyService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.ADMIN)
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody @Valid UpdatePolicyRequestDTO request
    ) {
        policyService.update(id, request);
        return Response.ok(null);
    }

}
