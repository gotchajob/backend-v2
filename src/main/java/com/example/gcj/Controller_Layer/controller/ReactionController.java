package com.example.gcj.Controller_Layer.controller;


import com.example.gcj.Service_Layer.dto.react.CreateReactionRequestDTO;
import com.example.gcj.Service_Layer.dto.react.ReactionResponseDTO;
import com.example.gcj.Service_Layer.service.ReactionService;
import com.example.gcj.Shared.util.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
@Tag(name = "Reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @GetMapping("")
    public Response<List<ReactionResponseDTO>> getReactionList() {
        List<ReactionResponseDTO> list = reactionService.getAll();
        return Response.ok(list);
    }


    @PostMapping("")
    public Response<String> createReaction(
            @RequestBody CreateReactionRequestDTO request
    ) {
        reactionService.create(request);
        return Response.ok(null);
    }

}
