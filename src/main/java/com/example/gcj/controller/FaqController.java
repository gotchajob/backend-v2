package com.example.gcj.controller;

import com.example.gcj.dto.faq.CreateFaqRequestDTO;
import com.example.gcj.dto.faq.FaqResponseDTO;
import com.example.gcj.dto.faq.UpdateFaqRequestDTO;
import com.example.gcj.dto.other.PageResponseDTO;
import com.example.gcj.service.FaqService;
import com.example.gcj.util.Response;
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
