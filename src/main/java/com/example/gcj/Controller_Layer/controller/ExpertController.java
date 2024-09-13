package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert.ExpertMatchListResponseDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateCostRequestDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertDescriptionRequestDTO;
import com.example.gcj.Service_Layer.dto.expert.UpdateExpertProfileRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.dto.user.ExpertAccountResponse;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

    @GetMapping("/match")
    @Operation(description = "each nation = 2 point, each experience = 3 -> 6 point, each skills = (sum rating + default point) / (number rating + 1) <br>" +
            "by: 1(nation), 2 (skill option), 3 (year experience)")
    public Response<List<ExpertMatchListResponseDTO>> matchExpert(
            @RequestParam(required = false) List<Long> skillOptionId,
            @RequestParam(required = false) List<String> nation,
            @RequestParam(required = false) Integer by,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int minYearExperience
    ) {
        List<ExpertMatchListResponseDTO> response = expertService.expertMatch(by, skillOptionId, nation, minYearExperience);
        return Response.ok(response);
    }

    @GetMapping("/match-v2")
    @Operation(description = "")
    public Response<List<ExpertMatchListResponseDTO>> matchExpertV2(
            @RequestParam(required = false) List<Long> skillOptionId,
            @RequestParam(required = false) List<String> nation,
            @RequestParam(required = false) Integer by,
            @RequestParam(required = false) @Min(1) Integer minYearExperience
    ) {
        List<ExpertMatchListResponseDTO> response = expertService.newExpertMatch(by, skillOptionId, nation, minYearExperience);
        return Response.ok(response);
    }

    @GetMapping("")
    @Operation(description = "sortBy: (field name):asc|desc. example: id:desc <br>" +
            "search: (key)(:|>|<)(value). example: id:11, bio:123")
    public Response<PageResponseDTO<ExpertAccountResponse>> getExpertList(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int limit,
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

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    public Response<ExpertAccountResponse> getCurrent(

    ) {
        ExpertAccountResponse response = expertService.getByCurrent();
        return Response.ok(response);
    }

    @PatchMapping("/current/update-cost")
    @Secured(Role.EXPERT)
    public Response<ExpertAccountResponse> updateCost(
            @RequestBody UpdateCostRequestDTO updateCostRequestDTO
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertService.updatePrice(expertId, updateCostRequestDTO.getCost());
        return Response.ok(null);
    }

    @PatchMapping("/update-profile")
    @Secured(Role.EXPERT)
    public Response<String> updateProfile(
            @RequestBody UpdateExpertProfileRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertService.updateProfile(expertId, request);
        return Response.ok(null);
    }

    @PatchMapping("/update-description")
    @Secured(Role.EXPERT)
    public Response<String> updateDescription(
            @RequestBody @Valid UpdateExpertDescriptionRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertService.updateDescription(expertId, request);
        return Response.ok(null);
    }


    @PatchMapping("/accept-booking")
    @Secured(Role.EXPERT)
    public Response<String> acceptBooking(
    ) {
        expertService.updateCurrentExpertStatus(1);
        return Response.ok(null);
    }

    @PatchMapping("/block-booking")
    @Secured(Role.EXPERT)
    public Response<String> blockBooking(
    ) {
        expertService.updateCurrentExpertStatus(3);
        return Response.ok(null);
    }

}
