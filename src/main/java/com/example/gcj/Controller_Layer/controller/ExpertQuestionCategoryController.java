package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_question_category.CreateExpertQuestionCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.ExpertQuestionCategoryResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_question_category.UpdateExpertQuestionCategoryRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertQuestionCategoryService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-question-category")
@RequiredArgsConstructor
public class ExpertQuestionCategoryController {
    private final ExpertQuestionCategoryService expertQuestionCategoryService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "finish")
    public Response<List<ExpertQuestionCategoryListResponseDTO>> get(
    ) {
        List<ExpertQuestionCategoryListResponseDTO> response = expertQuestionCategoryService.get();
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<List<ExpertQuestionCategoryListResponseDTO>> getByCurrent(
    ) {
        long expertId = expertService.getCurrentExpertId();
        List<ExpertQuestionCategoryListResponseDTO> response = expertQuestionCategoryService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "finish")
    public Response<ExpertQuestionCategoryResponseDTO> getById(
            @PathVariable long id
    ) {
        ExpertQuestionCategoryResponseDTO response = expertQuestionCategoryService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(description = "finish, role: expert")
    public Response<String> create(
            @RequestBody CreateExpertQuestionCategoryRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertQuestionCategoryService.create(expertId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateExpertQuestionCategoryRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertQuestionCategoryService.update(id, request, expertId);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertQuestionCategoryService.delete(id, expertId);
        return Response.ok(null);
    }

}
