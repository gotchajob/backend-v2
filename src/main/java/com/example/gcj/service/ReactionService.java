package com.example.gcj.service;

import com.example.gcj.dto.react.ReactionRequestDTO;
import com.example.gcj.dto.react.ReactionResponseDTO;

import java.util.List;

public interface ReactionService {
    List<ReactionResponseDTO> getAll();
    void create(ReactionRequestDTO request);
}
