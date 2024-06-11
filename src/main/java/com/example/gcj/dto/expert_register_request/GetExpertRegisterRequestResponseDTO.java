package com.example.gcj.dto.expert_register_request;

import com.example.gcj.model.ExpertRegisterRequest;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetExpertRegisterRequestResponseDTO implements Serializable {
    private List<ExpertRegisterRequestResponseDTO> list;
    private long total;

    public GetExpertRegisterRequestResponseDTO(List<ExpertRegisterRequest> expertRegisterRequests, long total) {
        this.list = new ArrayList<>();
        if (!expertRegisterRequests.isEmpty()) {
            for (ExpertRegisterRequest m : expertRegisterRequests) {
                this.list.add(new ExpertRegisterRequestResponseDTO(m));
            }
        }

        this.total = total;
    }
}
