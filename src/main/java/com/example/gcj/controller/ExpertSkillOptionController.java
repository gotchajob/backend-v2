package com.example.gcj.controller;

import com.example.gcj.service.ExpertSkillOptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expert-skill-option")
@RequiredArgsConstructor
@Tag(name = "Expert Skill Option")
public class ExpertSkillOptionController {
    private final ExpertSkillOptionService expertSkillOptionService;


}
