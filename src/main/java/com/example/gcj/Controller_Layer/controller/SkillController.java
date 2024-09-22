package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.Service_Layer.dto.skill.SkillResponseDTO;
import com.example.gcj.Service_Layer.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.Service_Layer.service.SkillService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
@Tag(name = "Skill Controller")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("")
    public Response<List<SkillResponseDTO>> get(
            @RequestParam(required = false) @Min(1) Long categoryId
    ) {
        List<SkillResponseDTO> skill = skillService.getAll(categoryId);
        return Response.ok(skill);
    }

    @GetMapping("/category/{categoryId}")
    public Response<List<SkillResponseDTO>> getSkillListByCategoryId(
            @PathVariable @Min(1) long categoryId
    ) {
        List<SkillResponseDTO> skills = skillService.findSkillByCategoryId(categoryId);
        return Response.ok(skills);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    public Response<String> createSkill(
            @RequestBody @Valid CreateSkillRequestDTO request
    ) {
        skillService.createSkill(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> updateSkill(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateSkillRequestDTO request
    ) {
        skillService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> deleteSkill(
            @PathVariable @Min(1) long id
    ) {
        skillService.deleteSkill(id);
        return Response.ok(null);
    }
}
