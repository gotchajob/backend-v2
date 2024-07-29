package com.example.gcj.controller;

import com.example.gcj.dto.expert_question_category.CreateExpertQuestionCategoryRequestDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryListResponseDTO;
import com.example.gcj.dto.expert_question_category.ExpertQuestionCategoryResponseDTO;
import com.example.gcj.service.ExpertQuestionCategoryService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Response;
import com.example.gcj.util.Role;
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
    @Operation(description = "coming soon")
    public Response<String> update(
            @PathVariable long id
    ) {

        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "finish")
    public Response<String> delete(
            @PathVariable long id
    ) {
        expertQuestionCategoryService.delete(id);
        return Response.ok(null);
    }

}
