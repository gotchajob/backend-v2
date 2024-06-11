package com.example.gcj.controller;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.expert.UpdateExpertRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.dto.user.ExpertAccountResponse;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

    @GetMapping("/match")
    public Response<List<ExpertMatchListResponseDTO>> matchExpert(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> skillOptionId,
            @RequestParam(required = false) List<String> nation,
            @RequestParam(required = false, defaultValue = "0") int minYearExperience


    ) {
        List<ExpertMatchListResponseDTO> response = expertService.expertMatch(categoryId, skillOptionId, nation, minYearExperience);
        return Response.ok(response);
    }

    @GetMapping("")
    @Operation(description = "sortBy: (field name):asc|desc. example: id:desc <br>" +
            "search: (key)(:|>|<)(value). example: id:11, bio:123")
    public Response<PageResponseDTO<ExpertAccountResponse>> getExpertList(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false, defaultValue = "12") @Min(1) int limit,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String... search
    ) {
        PageResponseDTO<ExpertAccountResponse> response = expertService.getExpert(page, limit, sortBy, search);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<ExpertAccountResponse> getExpert(
            @PathVariable long id
    ) {
        ExpertAccountResponse response = expertService.getExpert(id);
        return Response.ok(response);
    }

    @PatchMapping("/{id}")
    public Response<String> updateExpert(
            @PathVariable long id,
            @RequestBody UpdateExpertRequestDTO request
    ) {
        expertService.updateExpert(id, request);
        return null;
    }
}
