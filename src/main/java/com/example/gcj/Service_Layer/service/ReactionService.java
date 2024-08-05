package com.example.gcj.Service_Layer.service;

import com.example.gcj.Service_Layer.dto.react.CreateReactionRequestDTO;
import com.example.gcj.Service_Layer.dto.react.ReactionResponseDTO;

import java.util.List;

public interface ReactionService {
    List<ReactionResponseDTO> getAll();
    void create(CreateReactionRequestDTO request);
}
