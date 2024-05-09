package com.example.gcj.dto.mentor_register_request;

import com.example.gcj.model.MentorRegisterRequest;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public class MentorRegisterRequestResponseDTO implements Serializable {
    private long id;
    private String email;
    private Date createdAt;

    public MentorRegisterRequestResponseDTO(MentorRegisterRequest mentorRegisterRequest) {
        if (mentorRegisterRequest != null) {
            this.id = mentorRegisterRequest.getId();
            this.email = mentorRegisterRequest.getEmail();
            this.createdAt = mentorRegisterRequest.getCreatedAt();
        }
    }
}
