package com.example.gcj.dto.mentor_register_request;

import com.example.gcj.model.MentorRegisterRequest;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetMentorRegisterRequestResponseDTO implements Serializable {
    private List<MentorRegisterRequestResponseDTO> list;
    private long total;

    public GetMentorRegisterRequestResponseDTO(List<MentorRegisterRequest> mentorRegisterRequests, long total) {
        this.list = new ArrayList<>();
        if (!mentorRegisterRequests.isEmpty()) {
            for (MentorRegisterRequest m : mentorRegisterRequests) {
                this.list.add(new MentorRegisterRequestResponseDTO(m));
            }
        }

        this.total = total;
    }
}
