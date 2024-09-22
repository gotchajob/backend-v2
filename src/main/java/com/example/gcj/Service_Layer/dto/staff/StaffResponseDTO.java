package com.example.gcj.Service_Layer.dto.staff;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class StaffResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private int status;
    private Date createdAt;
}
