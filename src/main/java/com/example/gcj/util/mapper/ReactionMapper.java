package com.example.gcj.util.mapper;

import com.example.gcj.dto.react.ReactionResponseDTO;
import com.example.gcj.model.Reaction;

public class ReactionMapper {
    public static ReactionResponseDTO toDto(Reaction reaction) {
        return ReactionResponseDTO.builder()
                .icon(reaction.getIcon())
                .id(reaction.getId())
                .build();
    }
}
