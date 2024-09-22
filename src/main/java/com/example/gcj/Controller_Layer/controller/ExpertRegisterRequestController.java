package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_criteria.ExpertFormCriteriaResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_register_request.ApproveExpertRegisterRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_register_request.CreateExpertRegisterRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_register_request.ExpertRegisterRequestResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_register_request.RejectFromRegisterRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.CreateExpertAccountRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertFormCriteriaService;
import com.example.gcj.Service_Layer.service.ExpertRegisterRequestService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-register-request")
@RequiredArgsConstructor
@Tag(name = "Expert Register Request Controller")
public class ExpertRegisterRequestController {
    private final ExpertRegisterRequestService expertRegisterRequestService;
    private final ExpertFormCriteriaService expertFormCriteriaService;

    @PostMapping("")
    @Operation(summary = "admin, user")
    public Response<String> expertRegisterRequest(
            @RequestBody CreateExpertRegisterRequestDTO request
    ) {
        expertRegisterRequestService.create(request.getEmail());
        return Response.ok(null);
    }

    @GetMapping("")
    @Operation(description = "status: 0-deleted, 1-wait to process, 2-send form, 3-wait to approve form, 4-updating, 5-complete")
    public Response<PageResponseDTO<ExpertRegisterRequestResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<ExpertRegisterRequestResponseDTO> responseDTO = expertRegisterRequestService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(responseDTO);
    }

    @GetMapping("/{id}")
    @Operation(description = "status: 0-deleted, 1-wait to process, 2-send form, 3-wait to approve form, 4-updating, 5-complete")
    public Response<ExpertRegisterRequestResponseDTO> get(
            @PathVariable long id
    ) {
        ExpertRegisterRequestResponseDTO responseDTO = expertRegisterRequestService.get(id);
        return Response.ok(responseDTO);
    }

    @GetMapping("/{id}/check-url")
    @Operation(description = "status: 0-deleted, 1-wait to process, 2-send form, 3-wait to approve form, 4-updating, 5-complete <br>" +
            "status = 2 || 4 return 200 (valid to use form url)")
    public Response<String> checkRequest(
            @PathVariable long id
    ) {
        expertRegisterRequestService.checkRequest(id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/approve")
    @Secured(Role.STAFF)
    public Response<String> approveRegister(
            @PathVariable long id,
            @RequestBody ApproveExpertRegisterRequestDTO requestDTO
    ) {
        expertRegisterRequestService.approveRegister(id, requestDTO.getUrl());
        return Response.ok(null);
    }

    @PostMapping("/{id}/create_form")
    public Response<String> createForm(
            @PathVariable long id,
            @RequestBody CreateExpertAccountRequestDTO request
    ) {
        expertRegisterRequestService.createForm(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/approve-form")
    @Secured(Role.STAFF)
    public Response<String> approveRegisterForm(
            @PathVariable long id
    ) {
        expertRegisterRequestService.approveFormRegister(id);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/reject-form")
    @Secured(Role.STAFF)
    public Response<String> rejectRegister(
            @PathVariable long id,
            @RequestBody(required = false) RejectFromRegisterRequestDTO requestDTO
    ) {
        expertRegisterRequestService.rejectFormRegister(id, requestDTO);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/update-form")
    public Response<String> updateFormRegister(
            @PathVariable long id,
            @RequestBody UpdateExpertRequestDTO request
    ) {
        expertRegisterRequestService.updateForm(id, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/ban")
    @Secured(Role.STAFF)
    public Response<String> banRequest(
            @PathVariable long id
    ) {
        expertRegisterRequestService.banRequest(id);
        //todo: delete account expert
        return Response.ok(null);
    }

    @GetMapping("{id}/criteria")
    public Response<List<ExpertFormCriteriaResponseDTO>> getCriteria(
            @PathVariable @Min(1) Long id
    ) {
        List<ExpertFormCriteriaResponseDTO> response = expertFormCriteriaService.getList(id);
        return Response.ok(response);
    }

}
