package com.example.gcj.Service_Layer.dto.staff;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StaffListResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private int status;
}
