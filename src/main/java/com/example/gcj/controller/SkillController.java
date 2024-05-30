package com.example.gcj.controller;

import com.example.gcj.dto.skill.CreateSkillRequestDTO;
import com.example.gcj.dto.skill.SkillResponseDTO;
import com.example.gcj.dto.skill.UpdateSkillRequestDTO;
import com.example.gcj.model.Skill;
import com.example.gcj.service.SkillService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
@Tag(name = "Skill Controller")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("")
    public Response<List<SkillResponseDTO>> getSkillList() {
        List<SkillResponseDTO> skill = skillService.getAll();
        return Response.ok(skill);
    }

    @GetMapping("/category/{categoryId}")
    public Response<List<SkillResponseDTO>> getSkillListByCategoryId(
            @PathVariable long categoryId
    ) {
        List<SkillResponseDTO> skills = skillService.findSkillByCategoryId(categoryId);
        return Response.ok(skills);
    }

    @PostMapping("")
    public Response<String> createSkill(
            @RequestBody CreateSkillRequestDTO request
    ) {
        skillService.createSkill(request);
        return Response.ok(null);
    }

    @PutMapping("")
    public Response<String> updateSkill(
            @RequestBody List<UpdateSkillRequestDTO> request
    ) {
            skillService.updateSkill(request);
            return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteSkill(
            @PathVariable long id
    ) {
            skillService.deleteSkill(id);
            return Response.ok(null);
    }
}
