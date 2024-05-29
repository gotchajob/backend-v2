package com.example.gcj.controller;

import com.example.gcj.service.FaqService;
import com.example.gcj.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    @GetMapping("")
    public ResponseEntity<Response<String>> get(

    ) {
        try {

            return Response.success(null);
        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
