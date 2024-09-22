package com.example.gcj.Controller_Layer.controller;

import com.example.gcj.Service_Layer.dto.cv_category.CreateCvCategoryRequestDTO;
import com.example.gcj.Service_Layer.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.Service_Layer.service.CvCategoryService;
import com.example.gcj.Shared.util.Response;
import com.example.gcj.Shared.util.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cv-category")
@RequiredArgsConstructor
public class CvCategoryController {
    private final CvCategoryService cvCategoryService;

    @GetMapping("")
    public Response<List<CvCategoryListResponseDTO>> getList(

    ) {
        List<CvCategoryListResponseDTO> response = cvCategoryService.get();
        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<CvCategoryListResponseDTO> getById(
        @PathVariable @Min(1) long id
    ) {
        CvCategoryListResponseDTO response = cvCategoryService.getById(id);
        return Response.ok(response);
    }

    @PostMapping("")
    @Secured(Role.STAFF)
    public Response<String> create(
            @RequestBody @Valid CreateCvCategoryRequestDTO request
    ) {
        cvCategoryService.create(request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    @Secured(Role.STAFF)
    public Response<String> delete(
            @PathVariable @Min(1) long id
    ) {
        cvCategoryService.delete(id);
        return Response.ok(null);
    }
}
