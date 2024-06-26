package com.example.gcj.controller;

import com.example.gcj.dto.expert_skill_option.ExpertSkillOptionResponseDTO;
import com.example.gcj.dto.expert_skill_option.UpdateExpertSkillOptionPointRequestDTO;
import com.example.gcj.service.ExpertSkillOptionService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-skill-option")
@RequiredArgsConstructor
@Tag(name = "Expert Skill Option")
public class ExpertSkillOptionController {
    private final ExpertSkillOptionService expertSkillOptionService;
    private final UserService userService;

    @GetMapping("")
    public Response<List<ExpertSkillOptionResponseDTO>> getExpertSkillOption(
            @RequestParam long expertId
    ) {
        List<ExpertSkillOptionResponseDTO> response = expertSkillOptionService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @GetMapping("/current")
    public Response<List<ExpertSkillOptionResponseDTO>> getCurrent(
    ) {
        long expertId = userService.getCurrentExpertId();
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


}
