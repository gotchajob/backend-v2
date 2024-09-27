package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.expert_nation_support.CreateNationSupportRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportListRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertNationSupportService;
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
@RequestMapping("/expert-nation-support")
@RequiredArgsConstructor
public class ExpertNationSupportController {
    private final ExpertNationSupportService expertNationSupportService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(summary = "admin, user")
    public Response<List<ExpertNationSupportResponseDTO>> getExpertNationSupport(
            @RequestParam long expertId
    ) {
        List<ExpertNationSupportResponseDTO> response = expertNationSupportService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(summary = "admin, user")
    public Response<List<ExpertNationSupportResponseDTO>> getCurrent(
    ) {
        long expertId = expertService.getCurrentExpertId();
        List<ExpertNationSupportResponseDTO> response = expertNationSupportService.getByExpertId(expertId);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(summary = "")
    public Response<String> create(
            @RequestBody @Valid CreateNationSupportRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertNationSupportService.createNation(expertId, request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(summary = "")
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateNationSupportRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertNationSupportService.update(expertId, id, request);
        return Response.ok(null);
    }

    @PatchMapping("/update-list")
    @Secured(Role.EXPERT)
    @Operation(summary = "")
    public Response<String> update(
            @RequestBody UpdateNationSupportListRequestDTO nations
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertNationSupportService.update(expertId, nations);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(summary = "")
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        expertNationSupportService.delete(expertId, id);
        return Response.ok(null);
    }
}
