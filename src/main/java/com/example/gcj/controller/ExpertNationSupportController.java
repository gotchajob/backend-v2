package com.example.gcj.controller;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.service.ExpertNationSupportService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert-nation-support")
@RequiredArgsConstructor
public class ExpertNationSupportController {
    private final ExpertNationSupportService expertNationSupportService;

    @GetMapping("")
    @Operation(summary = "admin, user")
    public Response<List<ExpertNationSupportResponseDTO>> getExpertNationSupport(
            @RequestParam long expertId
    ) {
        List<ExpertNationSupportResponseDTO> response = expertNationSupportService.getByExpertId(expertId);
        return Response.ok(response);
    }
}
