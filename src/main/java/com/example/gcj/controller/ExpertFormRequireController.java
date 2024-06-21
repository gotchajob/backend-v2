package com.example.gcj.controller;

import com.example.gcj.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.service.ExpertFormRequireService;
import com.example.gcj.util.Response;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-form-require")
@RequiredArgsConstructor
public class ExpertFormRequireController {
    private final ExpertFormRequireService expertFormRequireService;

    @PostMapping("")
    public Response<String> create(
            @RequestBody CreateExpertFormRequireRequestDTO request
    ) {
        expertFormRequireService.create(request);
        return Response.ok(null);
    }

    @GetMapping("")
    public Response<List<ExpertFormRequireResponseDTO>> getList(
            @RequestParam(required = false) @Min(1) Long categoryId
    ) {
        List<ExpertFormRequireResponseDTO> response = expertFormRequireService.getList(categoryId);
        return Response.ok(response);
    }
}
