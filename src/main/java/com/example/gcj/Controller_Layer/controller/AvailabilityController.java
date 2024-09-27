package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.availability.AvailabilityListResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.AvailabilityResponseDTO;
import com.example.gcj.Service_Layer.dto.availability.CreateAvailabilityListRequestDTO;
import com.example.gcj.Service_Layer.service.AvailabilityService;
import com.example.gcj.Service_Layer.service.ExpertService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final ExpertService expertService;

    @GetMapping("")
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> get(
            @RequestParam(required = false) @Min(1) Long expertId
    ) {
        List<AvailabilityListResponseDTO> response = availabilityService.get(expertId);
        return Response.ok(response);
    }

    @GetMapping("/expert/{expertId}/valid-date-to-booking")
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> getValidDate(
            @PathVariable @Min(1) long expertId
    ) {
        List<AvailabilityListResponseDTO> response = availabilityService.getValidDateToBooking(expertId);
        return Response.ok(response);
    }

    @GetMapping("/current")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<List<AvailabilityListResponseDTO>> getByCurrent(

    ) {
        long expertId = expertService.getCurrentExpertId();
        List<AvailabilityListResponseDTO> response = availabilityService.get(expertId);
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(description = "")
    public Response<AvailabilityResponseDTO> getById(
            @PathVariable long id
    ) {
        AvailabilityResponseDTO responseDTO = availabilityService.getById(id);
        return Response.ok(responseDTO);
    }

    @PostMapping("")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<String> create(
        @RequestBody CreateAvailabilityListRequestDTO request
    ) {
        long expertId = expertService.getCurrentExpertId();
        availabilityService.create(expertId, request.getRequest());
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.EXPERT)
    @Operation(description = "")
    public Response<String> delete(
            @PathVariable long id
    ) {
        long expertId = expertService.getCurrentExpertId();
        availabilityService.delete(id, expertId);
        return Response.ok(null);
    }

}
