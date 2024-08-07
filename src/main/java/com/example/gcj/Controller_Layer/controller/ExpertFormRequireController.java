package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_form_require.CreateExpertFormRequireRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_form_require.ExpertFormRequireResponseDTO;
import com.example.gcj.Service_Layer.service.ExpertFormRequireService;
import com.example.gcj.Shared.util.Response;
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
            @RequestParam(required = false) List<Long> categoryIds
    ) {
        List<ExpertFormRequireResponseDTO> response = expertFormRequireService.getList(categoryIds);
        return Response.ok(response);
    }
}
