package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.UpdateExpertFormRequireRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertFormRequireService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-form-require")
@RequiredArgsConstructor
public class ExpertFormRequireController {
    private final ExpertFormRequireService expertFormRequireService;

    @PostMapping("")
    public Response<String> create(
            @RequestBody @Valid CreateExpertFormRequireRequestDTO request
    ) {
        expertFormRequireService.create(request);
        return Response.ok(null);
    }

    @GetMapping("")
    public Response<List<ExpertFormRequireResponseDTO>> getList(
            @RequestParam(required = false) List<Long> categoryIds
    ) {
        List<ExpertFormRequireResponseDTO> response = expertFormRequireService.getList(categoryIds);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateExpertFormRequireRequestDTO request
    ) {
        expertFormRequireService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        expertFormRequireService.delete(id);
        return Response.ok(null);
    }
}
