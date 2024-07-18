package com.example.gcj.controller;

import com.example.gcj.dto.policy.CreatePolicyRequestDTO;
import com.example.gcj.dto.policy.PolicyListResponseDTO;
import com.example.gcj.dto.policy.PolicyResponseDTO;
import com.example.gcj.dto.policy.UpdatePolicyRequestDTO;
import com.example.gcj.service.PolicyService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/{id}")//todo: finish this
    @Operation(description = "")
    public Response<PolicyResponseDTO> getById(
            @PathVariable long id
    ) {
        PolicyResponseDTO responseDTO = policyService.getById(id);
        return Response.ok(responseDTO);
    }
    @GetMapping("/key/{key}")//todo: finish this
    @Operation(description = "")
    public Response<PolicyResponseDTO> getByKey(
            @PathVariable String key
    ) {
        PolicyResponseDTO responseDTO = policyService.getByKey(key);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    //@Secured(Role.ADMIN)
    @Operation(description = "")
    public Response<String> create(
            @RequestBody CreatePolicyRequestDTO request
    ) {
        policyService.create(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    //@Secured(Role.ADMIN)
    @Operation(description = "")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdatePolicyRequestDTO request
    ) {
        policyService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured("role")//todo: finish this
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

}
