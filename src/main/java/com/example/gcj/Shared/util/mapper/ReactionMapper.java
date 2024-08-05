package com.example.gcj.Shared.util.mapper;

import com.example.gcj.Service_Layer.dto.react.ReactionResponseDTO;
import com.example.gcj.Repository_Layer.model.Reaction;

public class ReactionMapper {
    public static ReactionResponseDTO toDto(Reaction reaction) {
        return ReactionResponseDTO.builder()
                .icon(reaction.getIcon())
                .id(reaction.getId())
                .build();
    }
}
