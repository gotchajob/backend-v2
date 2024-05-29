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
    public ResponseEntity<Response<List<SkillResponseDTO>>> getSkillList(

    ) {
        try {
            List<SkillResponseDTO> skill = skillService.getAll();
            return Response.success(skill);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response<List<SkillResponseDTO>>> getSkillListByCategoryId(
            @PathVariable long categoryId
    ) {
        try {
            List<SkillResponseDTO> skills = skillService.findSkillByCategoryId(categoryId);
            return Response.success(skills);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<Response<String>> createSkill(
            @RequestBody CreateSkillRequestDTO request
    ) {
        try {
            skillService.createSkill(request);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<Response<String>> updateSkill(
            @PathVariable long id, @RequestBody UpdateSkillRequestDTO request
    ) {
        try {
            skillService.updateSkill(id, request);
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Response<String>> deleteSkill(
        @PathVariable long id
    ) {
        try {
            skillService.deleteSkill(id);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }
}
