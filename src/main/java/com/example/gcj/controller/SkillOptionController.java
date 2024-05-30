package com.example.gcj.controller;

import com.example.gcj.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.service.SkillOptionService;
import com.example.gcj.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill-option")
@RequiredArgsConstructor
public class SkillOptionController {
    private final SkillOptionService skillOptionService;

    @GetMapping("")
    public Response<List<SkillOptionResponseDTO>> getOptionList(
    ) {
        List<SkillOptionResponseDTO> skillOption = skillOptionService.getAll();
        return Response.ok(skillOption);
    }

    @GetMapping("/skill/{id}")
    public Response<List<SkillOptionResponseDTO>> getSkillOptionList(
            @PathVariable long id
    ) {
       List<SkillOptionResponseDTO> skillOption = skillOptionService.findSkillOptionBySkillId(id);
        return Response.ok(skillOption);
    }

    @PostMapping("")
    public Response<String> createSkillOption(
            @RequestBody CreateSkillOptionRequestDTO request
    ) {
        skillOptionService.createSkillOption(request);
        return Response.ok(null);
    }

    @PutMapping("")
    public Response<List<UpdateSkillOptionRequestDTO>> updateSkillOptions(
            @RequestBody List<UpdateSkillOptionRequestDTO> request
    ) {
        skillOptionService.updateSkillOptions(request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteSkillOptions(
            @PathVariable Long id
    ) {
        skillOptionService.deleteSkillOptions(id);
        return Response.ok(null);
    }
}
