package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv_template.*;
import com.example.gcj.Service_Layer.service.CvTemplateService;
import com.example.gcj.Service_Layer.service.UserService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cv-template")
@RestController
@RequiredArgsConstructor
public class CvTemplateController {
    private final CvTemplateService cvTemplateService;
    private final UserService userService;

    @GetMapping("")
    @Operation(description = "role: n/a")
    public Response<List<CvTemplateListResponseDTO>> getList(
            @RequestParam(required = false) @Min(1) Long categoryId
    ) {
        List<CvTemplateListResponseDTO> response = cvTemplateService.getList(categoryId);
        return Response.ok(response);
    }

    @GetMapping("/for-staff")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<List<CvTemplateListDetailResponseDTO>> getListForStaff(
            @RequestParam(required = false) @Min(1) Long categoryId,
            @RequestParam(required = false) @Min(0) Integer status
    ) {
        List<CvTemplateListDetailResponseDTO> response = cvTemplateService.getListForStaff(categoryId, status);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "role: n/a")
    public Response<CvTemplateResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        CvTemplateResponseDTO response = cvTemplateService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> create(
            @RequestBody @Valid CreateCvTemplateRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        cvTemplateService.create(userId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateCvTemplateRequestDTO request
    ) {
        cvTemplateService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    @Operation(description = "role: staff")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        cvTemplateService.delete(id);
        return Response.ok(null);
    }


}
