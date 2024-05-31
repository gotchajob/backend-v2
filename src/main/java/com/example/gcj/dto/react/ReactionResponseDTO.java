package com.example.gcj.dto.react;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReactionResponseDTO {
    private long id;
    private String icon;
}
