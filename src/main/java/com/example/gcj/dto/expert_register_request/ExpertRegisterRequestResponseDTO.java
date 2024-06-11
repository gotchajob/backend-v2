package com.example.gcj.dto.expert_register_request;

import com.example.gcj.model.ExpertRegisterRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExpertRegisterRequestResponseDTO implements Serializable {
    private long id;
    private String email;
    private Date createdAt;

    public ExpertRegisterRequestResponseDTO(ExpertRegisterRequest expertRegisterRequest) {
        if (expertRegisterRequest != null) {
            this.id = expertRegisterRequest.getId();
            this.email = expertRegisterRequest.getEmail();
            this.createdAt = expertRegisterRequest.getCreatedAt();
        }
    }
}
