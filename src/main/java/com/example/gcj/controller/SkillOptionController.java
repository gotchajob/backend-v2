package com.example.gcj.controller;

import com.example.gcj.dto.skill_option.CreateSkillOptionRequestDTO;
import com.example.gcj.dto.skill_option.SkillOptionResponseDTO;
import com.example.gcj.dto.skill_option.UpdateSkillOptionRequestDTO;
import com.example.gcj.model.SkillOption;
import com.example.gcj.service.SkillOptionService;
import com.example.gcj.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill-option")
@RequiredArgsConstructor
public class SkillOptionController {
    private final SkillOptionService skillOptionService;

    @GetMapping("")
    public ResponseEntity<Response<List<SkillOptionResponseDTO>>> getOptionList(
    ) {
        try {
            List<SkillOptionResponseDTO> skillOption = skillOptionService.getAll();
            return Response.success(skillOption);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<Response<String>> createSkillOption(
            @RequestBody CreateSkillOptionRequestDTO request
    ) {
        try {
            skillOptionService.createSkillOption(request);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PutMapping("")
    public ResponseEntity<Response<List<UpdateSkillOptionRequestDTO>>> updateSkillOptions(
            @RequestBody List<UpdateSkillOptionRequestDTO> request
    ) {
        try {
            skillOptionService.updateSkillOptions(request);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Response<String>> deleteSkillOptions(
            @RequestBody List<Long> skillIds
    ) {
        try {
            boolean success = skillOptionService.deleteSkillOptions(skillIds);
            if (success) {
                return Response.success(null);
            } else {
                return Response.error(null);
            }
        } catch (Exception e) {
            return Response.error(e);
        }
    }
}
