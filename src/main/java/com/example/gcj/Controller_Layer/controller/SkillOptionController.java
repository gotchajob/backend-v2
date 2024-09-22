package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.Service_Layer.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.Service_Layer.service.SkillOptionService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill-option")
@RequiredArgsConstructor
public class SkillOptionController {
    private final SkillOptionService skillOptionService;

    @GetMapping("")
    public Response<List<SkillOptionResponseDTO>> getOptionList(
            @RequestParam(required = false) @Min(1) Long categoryId,
            @RequestParam(required = false) @Min(1) Long skillId
    ) {
        List<SkillOptionResponseDTO> response = skillOptionService.getAll(categoryId, skillId);
        return Response.ok(response);
    }

    @GetMapping("/skill/{id}")
    public Response<List<SkillOptionResponseDTO>> getSkillOptionList(
            @PathVariable @Min(1) long id
    ) {
       List<SkillOptionResponseDTO> skillOption = skillOptionService.findSkillOptionBySkillId(id);
        return Response.ok(skillOption);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    public Response<String> createSkillOption(
            @RequestBody @Valid CreateSkillOptionRequestDTO request
    ) {
        skillOptionService.createSkillOption(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> updateSkillOptions(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateSkillOptionRequestDTO request
    ) {
        skillOptionService.update(id, request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> deleteSkillOptions(
            @PathVariable @Min(1) long id
    ) {
        skillOptionService.deleteSkillOptions(id);
        return Response.ok(null);
    }
}
