package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_rating.ExpertSkillRatingTotalRatingResponseDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Service_Layer.service.ExpertSkillRatingService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-skill-rating")
@RequiredArgsConstructor
public class ExpertSkillRatingController {
    private final ExpertSkillRatingService expertSkillRatingService;
    private final ExpertService expertService;

    @GetMapping("/total-rating")
    @Operation(description = "finish")
    public Response<List<ExpertSkillRatingTotalRatingResponseDTO>> getTotalRating(
            @RequestParam long expertSkillOptionId
    ) {
        List<ExpertSkillRatingTotalRatingResponseDTO> response = expertSkillRatingService.getTotalRating(expertSkillOptionId);
        return Response.ok(response);
    }

    @GetMapping("/for-expert")
    @Secured(Role.EXPERT)
    @Operation(description = "finish. role: expert")
    public Response<PageResponseDTO<ExpertSkillRatingResponseDTO>> getListByExpert(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam long expertSkillOptionId
    ) {
        long expertId = expertService.getCurrentExpertId();
        PageResponseDTO<ExpertSkillRatingResponseDTO> response = expertSkillRatingService.getByExpert(pageNumber, pageSize, sortBy, expertId, expertSkillOptionId);
        return Response.ok(response);
    }

    @GetMapping("")
    @Operation(description = "finish")
    public Response<PageResponseDTO<ExpertSkillRatingResponseDTO>> getListByExpert(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize,
            @RequestParam(required = false, defaultValue = "createdAt:desc") String sortBy,
            @RequestParam(required = false) @Min(1) Long expertSkillOptionId
    ) {
        String[] search = new String[]{
                expertSkillOptionId == null ? "" : "expertSkillOptionId:" + expertSkillOptionId
        };

        PageResponseDTO<ExpertSkillRatingResponseDTO> response = expertSkillRatingService.get(pageNumber, pageSize, sortBy, search);
        return Response.ok(response);
    }

}
