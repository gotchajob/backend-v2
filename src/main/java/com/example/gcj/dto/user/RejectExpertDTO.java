package com.example.gcj.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RejectExpertDTO {
    private Long id;
    private String note;
    private String url;
}
