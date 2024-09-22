package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.faq.FaqResponseDTO;
import com.example.gcj.Service_Layer.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.FaqService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    @GetMapping("")
    public Response<PageResponseDTO<FaqResponseDTO>> get(
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageNumber,
            @RequestParam(required = false, defaultValue = "6") @Min(1) int pageSize
    ) {
        PageResponseDTO<FaqResponseDTO> response =  faqService.getList(pageNumber, pageSize);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    public Response<String> createFaq(
            @RequestBody @Valid CreateFaqRequestDTO request
    ) {
        faqService.createFaq(request);
        return Response.ok(null);
    }

    @PatchMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> update(
            @PathVariable @Min(1) long id,
            @RequestBody @Valid UpdateFaqRequestDTO request
    ) {
        faqService.updateFaq(id, request);
        return Response.ok(null);
    }

}
