package com.example.gcj.controller;

import com.example.gcj.dto.cv_template.CreateCvTemplateRequestDTO;
import com.example.gcj.dto.cv_template.CvTemplateListResponseDTO;
import com.example.gcj.dto.cv_template.CvTemplateResponseDTO;
import com.example.gcj.dto.cv_template.UpdateCvTemplateRequestDTO;
import com.example.gcj.service.CvTemplateService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/{id}")
    @Operation(description = "role: n/a")
    public Response<CvTemplateResponseDTO> getById(
            @PathVariable @Min(1) long id
    ) {
        CvTemplateResponseDTO response = cvTemplateService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.ADMIN)
    @Operation(description = "role: admin")
    public Response<String> create(
            @RequestBody CreateCvTemplateRequestDTO request
    ) {
        long userId = userService.getCurrentUserId();
        cvTemplateService.create(userId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.ADMIN)
    @Operation(description = "role: admin")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody UpdateCvTemplateRequestDTO request
    ) {
        cvTemplateService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.ADMIN)
    @Operation(description = "role: admin")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        cvTemplateService.delete(id);
        return Response.ok(null);
    }


}
