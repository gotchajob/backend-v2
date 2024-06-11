package com.example.gcj.dto.expert_request_reject;

import lombok.Data;

@Data
public class ExpertRegisterUpdateRequestDTO {
    private long requestId;
    private String note;
    private String updateUrl;
    private Integer status;

}
