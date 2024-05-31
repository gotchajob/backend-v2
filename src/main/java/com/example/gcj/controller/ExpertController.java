package com.example.gcj.controller;

import com.example.gcj.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.dto.user.UserProfileDTO;
import com.example.gcj.model.User;
import com.example.gcj.service.ExpertService;
import com.example.gcj.util.Response;
import com.example.gcj.util.mapper.UserMapper;
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
            @RequestParam(required = false) long categoryId,
            @RequestParam(required = false) List<Long> skillOptionId,
            @RequestParam(required = false) List<String> nation,
            @RequestParam(required = false, defaultValue = "0") int minYearExperience


    ) {
        List<ExpertMatchListResponseDTO> response = expertService.expertMatch(categoryId, skillOptionId, nation, minYearExperience);
        return Response.ok(response);
    }
}
