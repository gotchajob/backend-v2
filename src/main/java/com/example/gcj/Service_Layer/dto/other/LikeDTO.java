package com.example.gcj.Service_Layer.dto.other;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LikeDTO {
    private boolean liked;
    private long value;
}
