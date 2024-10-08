package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Service_Layer.service.ExpertSkillOptionService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-skill-option")
@RequiredArgsConstructor
@Tag(name = "Expert Skill Option")
public class ExpertSkillOptionController {
    private final ExpertSkillOptionService expertSkillOptionService;
    private final ExpertService expertService;

    @GetMapping("")
    public Response<List<ExpertSkillOptionResponseDTO>> getExpertSkillOption(
            @RequestParam @Min(1) long expertId
    ) {
        List<ExpertSkillOptionResponseDTO> response = expertSkillOptionService.getByExpertId(expertId, 1);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    public Response<List<ExpertSkillOptionResponseDTO>> getCurrent(
    ) {
        long expertId = expertService.getCurrentExpertId();
        List<ExpertSkillOptionResponseDTO> response = expertSkillOptionService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @PutMapping("")
    public Response<String> updateDefaultPoint(
            @RequestBody List<UpdateExpertSkillOptionPointRequestDTO> request
    ) {
        expertSkillOptionService.updateDefaultPoint(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/hidden")
    @Secured(Role.EXPERT)
    public Response<String> hide(
            @PathVariable @Min(1) long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertSkillOptionService.hidden(id, expertId);
        return Response.ok(null);
    }

    @PatchMapping("/{id}/show")
    @Secured(Role.EXPERT)
    public Response<String> show(
            @PathVariable @Min(1) long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertSkillOptionService.show(id, expertId);
        return Response.ok(null);
    }


}
