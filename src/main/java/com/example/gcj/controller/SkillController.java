package com.example.gcj.controller;

import com.example.gcj.service.SkillService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
@Tag(name = "Skill Controller")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("")
    public ResponseEntity<Response<String>> getList(

    ) {
        try {
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<Response<String>> createSkill(

    ) {
        try {
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @PutMapping("")
    public ResponseEntity<Response<String>> updateSkill(

    ) {
        try {
            return Response.success(null);

        } catch (Exception e) {
            return Response.error(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Response<String>> deleteSkill(

    ) {
        try {
            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }
}
