package com.example.gcj.controller;

import com.example.gcj.dto.cv.CVListResponseDTO;
import com.example.gcj.dto.cv_category.CreateCvCategoryRequestDTO;
import com.example.gcj.dto.cv_category.CvCategoryListResponseDTO;
import com.example.gcj.service.CvCategoryService;
import com.example.gcj.util.Response;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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
    public Response<String> create(
            @RequestBody CreateCvCategoryRequestDTO request
    ) {
        cvCategoryService.create(request);
        return Response.ok(null);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(
            @PathVariable long id
    ) {
        cvCategoryService.delete(id);
        return Response.ok(null);
    }
}
