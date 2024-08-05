package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.faq.FaqResponseDTO;
import com.example.gcj.Service_Layer.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.Service_Layer.dto.other.PageResponseDTO;
import com.example.gcj.Service_Layer.service.FaqService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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
    public Response<String> createFaq(
            @RequestBody CreateFaqRequestDTO request
    ) {
        faqService.createFaq(request);
        return Response.ok(null);
    }

    @PutMapping("/{id}")
    public Response<String> update(
            @PathVariable long id,
            @RequestBody UpdateFaqRequestDTO request
    ) {
        faqService.updateFaq(id, request);
        return Response.ok(null);
    }

}
