package com.example.gcj.Service_Layer.dto.react;

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
